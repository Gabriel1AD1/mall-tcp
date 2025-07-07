package com.labotec.pe.infra.factory.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.app.constants.util.TypeDataPacket;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.infra.server.MessageHandler;

import java.util.Optional;

import static com.labotec.pe.app.constants.service.DataPacketError.STRUCTURE_ERROR;
import static com.labotec.pe.app.factory.ErrorDataPacketFactory.getErrorForDataPacket;

@Slf4j
public class TcpProfileUtils {
    public static void closeChanel(ChannelHandlerContext ctx, TypeDataPacket typeDataPacket) {
        Optional<String> messageError = getErrorForDataPacket(typeDataPacket, STRUCTURE_ERROR);
        messageError.ifPresent(s -> sendResponse(ctx, s));
        if (messageError.isEmpty()) ctx.close();
    }

    public static void authHandlerDevice(AuthDeviceResponse authData, ChannelHandlerContext ctx) {
        log.info("Estado de autenticación: {}", authData);
        sendResponse(ctx, authData.getCodeStatus());  // Usar el método para enviar la respuesta
        // Si la autenticación falla o no está autorizada, cerrar la conexión
        if (authData.getCodeStatus().equals(StatusLogin.AUTH_FAILED) ||
                authData.getCodeStatus().equals(StatusLogin.AUTH_NOT_AUTHORIZED)) {
            log.warn("Autenticación fallida para el cliente. Cerrando la conexión...");
            ctx.close(); // Cierra la conexión
        }
    }

    public static void sendResponse(ChannelHandlerContext ctx, String message) {
        // Convierte el mensaje a ByteBuf
        ByteBuf responseBuf = MessageHandler.handleOutput(message);

        // Envía el mensaje al cliente con un listener para manejar errores
        ctx.write(responseBuf).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        ctx.flush();
    }
}
