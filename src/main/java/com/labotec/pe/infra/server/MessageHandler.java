package com.labotec.pe.infra.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class MessageHandler {


    // Método para manejar la entrada de datos (recibe ByteBuf y devuelve String)
    public static String handleInput(ByteBuf byteBuf) {
        return byteBuf.toString(CharsetUtil.UTF_8);  // Decodificar el contenido en UTF-8
    }

    // Método para manejar la salida de datos (recibe String y devuelve ByteBuf)
    public static ByteBuf handleOutput(String response) {
        return Unpooled.copiedBuffer(response, CharsetUtil.UTF_8);
    }
}
