package com.labotec.pe.infra.server;


import com.labotec.pe.domain.model.AuthDeviceResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveChannelsRegistry {

    private static final Map<AuthDeviceResponse, ChannelHandlerContext> channelsByImei = new ConcurrentHashMap<>();

    public static void add(AuthDeviceResponse imei, ChannelHandlerContext channel) {
        channelsByImei.put(imei, channel);
    }

    public static void remove(String imei) {
        channelsByImei.remove(AuthDeviceResponse.of(imei));
    }

    public static Optional<ChannelHandlerContext> getChannel(String imei) {
        return Optional.ofNullable(channelsByImei.get(AuthDeviceResponse.of(imei)));
    }

    public static Map<AuthDeviceResponse, ChannelHandlerContext> getAll() {
        return channelsByImei;
    }
    public static List<AuthDeviceResponse> getAllDevices() {
        return List.copyOf(channelsByImei.keySet());
    }
    public static int count() {
        return channelsByImei.size();
    }

    public static boolean isOnline(String imei) {
        return channelsByImei.containsKey(AuthDeviceResponse.of(imei));
    }

    public static void removeByChannel(ChannelHandlerContext channel) {
        channelsByImei.entrySet().removeIf(entry -> entry.getValue().equals(channel));
    }

}
