package com.labotec.pe.infra.server.adapter;

import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.app.port.output.tcp.TcpDeviceService;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import com.labotec.pe.infra.server.MessageHandler;
import io.netty.channel.ChannelFutureListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdapterTcpDeviceService implements TcpDeviceService {
    public static final String LINE_BREAK = "\r\n";

    @Override
    public boolean sendCommand(String imei, String command) {
        ActiveChannelsRegistry.getChannel(imei).ifPresent(
                channelHandlerContext -> {
                    // Convierte el mensaje a ByteBuf
                    var responseBuf = MessageHandler.handleOutput(command + LINE_BREAK);
                    // Envía el mensaje al cliente con un listener para manejar errores
                    channelHandlerContext.write(responseBuf).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    channelHandlerContext.flush();
                }
        );
        return ActiveChannelsRegistry.getChannel(imei).isPresent();
    }
}
