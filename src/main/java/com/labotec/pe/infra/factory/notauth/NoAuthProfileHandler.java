package com.labotec.pe.infra.factory.notauth;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import com.labotec.pe.app.constants.util.TypeDataPacket;
import com.labotec.pe.app.port.output.PositionService;
import com.labotec.pe.app.util.MessageTypeExtractor;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.DataPosition;
import com.labotec.pe.infra.factory.TcpProfileHandler;
import com.labotec.pe.infra.metrics.TCPMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.labotec.pe.infra.factory.utils.TcpProfileUtils.closeChanel;
import static com.labotec.pe.infra.factory.utils.TcpProfileUtils.sendResponse;
import static com.labotec.pe.infra.server.TCPServerHandler.AUTH_RESPONSE_KEY;

@Component("NO_AUTH")
@RequiredArgsConstructor
public class NoAuthProfileHandler implements TcpProfileHandler {
    private static final Logger log = LoggerFactory.getLogger(NoAuthProfileHandler.class);
    private final PositionService deviceService;
    private final TCPMetrics tcpMetrics;
    @Override
    public void handleMessage(ChannelHandlerContext ctx, String message) {
        log.info("Ejecutando perfil no auth .......");
        log.info("Mensaje del cliente : {}", message);
        TypeDataPacket typeDataPacket = MessageTypeExtractor.getMessageType(message);
        try{
            if
            (
                    Set.of(TypeDataPacket.EXTENDED, TypeDataPacket.MESSAGE, TypeDataPacket.SHORT)
                    .contains(typeDataPacket)
            ) {

                // Recuperar AuthDeviceResponse desde la sesión
                AuthDeviceResponse authResponseFromSession =
                        ctx.channel().attr(AUTH_RESPONSE_KEY).get();

                if (authResponseFromSession != null) {
                    log.info("Auth recuperado de la sesión: {}", authResponseFromSession);
                } else {
                    log.warn("Auth no encontrado en la sesión.");
                    closeChanel(ctx,typeDataPacket);
                    return;
                }

                DataPosition dataPacket = deviceService.getDataPacket(message, authResponseFromSession.getImei());
                log.info("DataPacket procesado : {}", dataPacket);

                sendResponse(ctx, dataPacket.getMessageDecode());
            }
        }catch (IllegalArgumentException ex){
            log.warn("Mensaje enviado incorrectamente ");
            closeChanel(ctx,typeDataPacket);
        }
    }
}
