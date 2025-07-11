package com.labotec.pe.infra.schedule;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.config.ConfigConstant;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import com.labotec.pe.infra.services.ConnectionsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class KeepAliveSchedule {
    private final ConnectionsServices connectionsServices;
    private final ConfigConstant configConstant;
    @Scheduled(fixedRate = 60_000) // cada 60 segundos
    public void keepAlive() {
        ActiveChannelsRegistry.getAllDevices().forEach(device -> {
            Instant lastSeen = device.getTimestamp();
            Instant now = Instant.now();
            //Si el dispositivo no esta activo por tantos minutos se considera en estado desconocido
            if (now.minusSeconds(configConstant.getMinusSecond()).isAfter(lastSeen)&&!device.getDeviceStatus().equals(DeviceStatus.unknown)) {
                device.unknown();
                connectionsServices.deviceUnknown(device);
            }
        });

    }


}
