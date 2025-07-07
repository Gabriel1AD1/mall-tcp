package com.labotec.pe.infra.server;

import com.labotec.pe.domain.model.DeviceContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.infra.config.AppConfigConstant;
import com.labotec.pe.infra.factory.TCPServerHandlerFactory;
import com.labotec.pe.infra.metrics.TCPMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(TCPServerHandler.class);
    private final AppConfigConstant appConfigConstant;
    private final TCPMetrics tcpMetrics;
    private final TCPServerHandlerFactory tcpServerHandlerFactory;
    // Clave para almacenar y recuperar AuthDeviceResponse
    public static final AttributeKey<AuthDeviceResponse> AUTH_RESPONSE_KEY =
            AttributeKey.valueOf("authResponse");
    private static final long INACTIVITY_TIMEOUT = 1L; // Tiempo de inactividad en minutos

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // Recupera el imei desde el canal antes de cerrar
        AuthDeviceResponse auth = ctx.channel().attr(TCPServerHandler.AUTH_RESPONSE_KEY).get();
        if (auth != null) {
            log.info("El cliente con IMEI {} se ha desconectado.", auth.getImei());
            ActiveChannelsRegistry.remove(auth.getImei());
            DeviceContext.getInstance().removeDeviceByImei(auth.getImei()); // opcional
        }
        tcpMetrics.desactiveSession();
        log.warn("La conexión con el cliente se ha cerrado.");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            String message = MessageHandler.handleInput((ByteBuf) msg);
            tcpMetrics.incrementTcpRequests();
            tcpServerHandlerFactory.getHandler(appConfigConstant.getProfileServerTcp()).handleMessage(ctx, message);
        } finally {
            ReferenceCountUtil.release(msg); // Libera el buffer
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error en TCPServerHandler: {}", cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new IdleStateHandler(1, 0, 0, TimeUnit.MINUTES));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.warn("No se ha recibido mensaje del cliente durante {} minuto(s). Cerrando la conexión.", INACTIVITY_TIMEOUT);
            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}