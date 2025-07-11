package com.labotec.pe.infra.factory.pro.sincronous.rest;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.app.constants.util.TypeDataPacket;
import com.labotec.pe.app.port.output.PositionRepository;
import com.labotec.pe.app.port.input.AuthDeviceService;
import com.labotec.pe.app.port.input.PositionService;
import com.labotec.pe.app.util.JsonUtil;
import com.labotec.pe.app.util.LoginFactory;
import com.labotec.pe.app.util.MessageTypeExtractor;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.DataPosition;
import com.labotec.pe.domain.model.LoginWialon;
import com.labotec.pe.infra.channels.rest.RestProduccer;
import com.labotec.pe.infra.factory.TcpProfileHandler;
import com.labotec.pe.infra.metrics.TCPMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.labotec.pe.infra.factory.utils.TcpProfileUtils.*;
import static com.labotec.pe.infra.factory.utils.TcpProfileUtils.closeChanel;
import static com.labotec.pe.infra.server.TCPServerHandler.AUTH_RESPONSE_KEY;

@Component("PRODUCTION_REST")
@RequiredArgsConstructor
public class RestProfileHandler implements TcpProfileHandler {
    private static final Logger log = LoggerFactory.getLogger(RestProfileHandler.class);
    private final AuthDeviceService authDeviceService;
    private final DeviceService deviceService;
    private final PositionService positionService;
    private final RestProduccer restProduccer;
    private final PositionRepository positionRepository;
    private final TCPMetrics tcpMetrics;
    @Override
    public void handleMessage(ChannelHandlerContext ctx, String message) {

        log.info("Ejecutando perfil produccion con rest .......");

        log.debug(" Mensaje del cliente: {}", message);
        TypeDataPacket typeDataPacket = MessageTypeExtractor.getMessageType(message);

        try {
            if (typeDataPacket.equals(TypeDataPacket.LOGIN)) {
                LoginWialon loginWialon = LoginFactory.getLogin(message);
                log.info("Login del vehículo: {}", loginWialon);

                AuthDeviceResponse authData = authDeviceService.getAuthorization(loginWialon);

                // Manejar autenticación y asociar objeto
                authHandlerDevice(authData, ctx);
                if (authData.getCodeStatus().equals(StatusLogin.AUTH_SUCCESSFUL)) {
                    associateObjectWithSession(ctx.channel(), authData);
                }
            }

            if (Set.of(
                    TypeDataPacket.EXTENDED,
                    TypeDataPacket.MESSAGE,
                    TypeDataPacket.SHORT
            ).contains(typeDataPacket)) {

                // Recuperar AuthDeviceResponse desde la sesión
                AuthDeviceResponse authResponseFromSession =
                        ctx.channel().attr(AUTH_RESPONSE_KEY).get();

                if (verifySession(ctx, authResponseFromSession, typeDataPacket)) return;

                DataPosition dataPacket = positionService.getDataPacket(message, authResponseFromSession.getImei());
                log.info("DataPacket procesado: {}", dataPacket);
                restProduccer.sendMessage(dataPacket.getPosition());
                // Enviar posición al Kafka
                positionRepository.saveOrUpdate(dataPacket.getPosition().getImei(), JsonUtil.toJson(dataPacket.getPosition()));
                sendResponse(ctx, dataPacket.getMessageDecode());
            }
        } catch (IllegalArgumentException ex) {
            log.warn("Mensaje enviado incorrectamente");
            closeChanel(ctx, typeDataPacket);
        } catch (Exception e){
            closeChanel(ctx,typeDataPacket);
        }
    }

    private static boolean verifySession(ChannelHandlerContext ctx, AuthDeviceResponse authResponseFromSession, TypeDataPacket typeDataPacket) {
        if (authResponseFromSession != null) {
            log.info("AuthDeviceResponse recuperado de la sesión: {}", authResponseFromSession);
        } else {
            log.warn("AuthDeviceResponse no encontrado en la sesión.");
            closeChanel(ctx, typeDataPacket);
            return true;
        }
        return false;
    }

    private void associateObjectWithSession(Channel channel, AuthDeviceResponse authData) {
        // Asociar objeto AuthDeviceResponse al canal
        channel.attr(AUTH_RESPONSE_KEY).set(authData);
        tcpMetrics.incrementTcpConnections();
        log.info("AuthDeviceResponse asociado con la sesión: {}", authData);
    }

}
