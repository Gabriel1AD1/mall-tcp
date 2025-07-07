package com.labotec.pe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
public class DeviceContext {

    private Set<DeviceOnline> deviceOnline = Collections.synchronizedSet(new HashSet<>());

    // Singleton único
    private static final DeviceContext INSTANCE = new DeviceContext();

    private DeviceContext() {
        // Constructor privado
    }

    public static DeviceContext getInstance() {
        return INSTANCE;
    }

    // Métodos utilitarios opcionales
    public void addDevice(String imei) {
        deviceOnline.add(DeviceOnline.builder()
                .imei(imei)
                .timestamp(Instant.now()).build());
    }

    public void removeDeviceByImei(String imei) {
        deviceOnline.removeIf(d -> d.getImei().equals(imei));
    }

    public boolean isOnline(String imei) {
        return deviceOnline.stream().anyMatch(d -> d.getImei().equals(imei));
    }

    // Clase interna
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceOnline {
        private String imei;
        private Instant timestamp;
    }
}
