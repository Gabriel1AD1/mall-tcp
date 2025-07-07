package com.labotec.pe.infra.factory;

import io.netty.channel.ChannelHandlerContext;

public interface TcpProfileHandler {
    void handleMessage(ChannelHandlerContext ctx, String message);

}
