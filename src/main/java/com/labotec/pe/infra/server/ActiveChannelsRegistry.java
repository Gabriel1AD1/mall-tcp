package com.labotec.pe.infra.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveChannelsRegistry {

    private static final Map<String, ChannelHandlerContext> channelsByImei = new ConcurrentHashMap<>();

    public static void add(String imei, ChannelHandlerContext channel) {
        channelsByImei.put(imei, channel);
    }

    public static void remove(String imei) {
        channelsByImei.remove(imei);
    }

    public static ChannelHandlerContext getChannel(String imei) {
        return channelsByImei.get(imei);
    }

    public static Map<String, ChannelHandlerContext> getAll() {
        return channelsByImei;
    }

    public static int count() {
        return channelsByImei.size();
    }

    public static boolean isOnline(String imei) {
        return channelsByImei.containsKey(imei);
    }

    public static void removeByChannel(ChannelHandlerContext channel) {
        channelsByImei.entrySet().removeIf(entry -> entry.getValue().equals(channel));
    }

}
