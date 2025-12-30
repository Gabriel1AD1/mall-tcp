package com.labotec.pe.infra.server.adapter;

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
  public static final String TYPE_MESSAGE = "#M#";

  @Override
  public boolean sendCommand(String imei, String command) {
    ActiveChannelsRegistry.getChannel(imei)
        .ifPresent(
            channelHandlerContext -> {
              // Convierte el mensaje a ByteBuf
              var responseBuf = MessageHandler.handleOutput(TYPE_MESSAGE + command + LINE_BREAK);
              // Envía el mensaje al cliente con un listener para manejar errores
              channelHandlerContext
                  .write(responseBuf)
                  .addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
              channelHandlerContext.flush();
            });
    return ActiveChannelsRegistry.getChannel(imei).isPresent();
  }
}
