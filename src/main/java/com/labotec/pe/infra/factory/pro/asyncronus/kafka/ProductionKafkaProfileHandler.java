package com.labotec.pe.infra.factory.pro.asyncronus.kafka;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import com.labotec.pe.infra.services.ConnectionsServices;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.app.constants.util.TypeDataPacket;
import com.labotec.pe.app.port.output.PositionRepository;
import com.labotec.pe.app.port.input.AuthDeviceService;
import com.labotec.pe.app.port.input.PositionService;
import com.labotec.pe.app.util.LoginFactory;
import com.labotec.pe.app.util.MessageTypeExtractor;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.DataPosition;
import com.labotec.pe.domain.model.LoginWialon;
import com.labotec.pe.infra.channels.kafka.productor.positions.KafkaProducerPositionService;
import com.labotec.pe.infra.factory.TcpProfileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.labotec.pe.infra.factory.utils.TcpProfileUtils.*;
import static com.labotec.pe.infra.server.TCPServerHandler.AUTH_RESPONSE_KEY;


@Component("PRODUCTION_KAFKA")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class ProductionKafkaProfileHandler implements TcpProfileHandler {
    private static final Logger log = LoggerFactory.getLogger(ProductionKafkaProfileHandler.class);
    private final AuthDeviceService authDeviceService;
    private final DeviceService deviceService;
    private final PositionService positionService;
    private final KafkaProducerPositionService kafkaProducerPositionService;
    private final ConnectionsServices connectionsServices;
    private final PositionRepository positionRepository;


    @Override
    public void handleMessage(ChannelHandlerContext
                                          ctx , String message){
        log.info("Ejecutando perfil produccion con kafka .......");

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
                    associateObjectWithSession(ctx, authData);
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

                if (authResponseFromSession == null) {
                    log.warn("AuthDeviceResponse no encontrado en la sesión.");

                    closeChanel(ctx,typeDataPacket);
                    return;
                }
                log.info("AuthDeviceResponse recuperado de la sesión: {}", authResponseFromSession);

                DataPosition dataPacket = positionService.getDataPacket(message, authResponseFromSession.getImei());
                log.info("DataPacket procesado: {}", dataPacket);

                kafkaProducerPositionService.sendPosition(dataPacket.getPosition());
                sendResponse(ctx, dataPacket.getMessageDecode());
                authResponseFromSession.online();
            }
        } catch (IllegalArgumentException ex) {
            log.warn("Mensaje enviado incorrectamente");
            closeChanel(ctx, typeDataPacket);
        }
    }
    private void associateObjectWithSession(ChannelHandlerContext channel, AuthDeviceResponse authData) {
        // Asociar objeto AuthDeviceResponse al canal
        channel.channel().attr(AUTH_RESPONSE_KEY).set(authData);
        connectionsServices.deviceConnected(authData);

        ActiveChannelsRegistry.add(authData, channel);
        log.info("AuthDeviceResponse asociado con la sesión: {}", authData);
    }
}
