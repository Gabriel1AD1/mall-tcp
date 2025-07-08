package com.labotec.pe.infra.schedule;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.config.ConfigConstant;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class KeepAliveSchedule {
    private final DeviceService deviceService;
    private final ConfigConstant configConstant;
    @Scheduled(fixedRate = 60_000) // cada 60 segundos
    public void keepAlive() {
        ActiveChannelsRegistry.getAllDevices().forEach(device -> {
            Instant lastSeen = device.getTimestamp();
            Instant now = Instant.now();

            //Si el dispositivo no esta activo por tantos minutos se considera en estado desconocido
            if (now.minusSeconds(configConstant.getMinusSecond()).isAfter(lastSeen)) {
                deviceService.updateStatus(device.getId(), DeviceStatus.unknown); // tu lógica adicional
            }
        });

    }


}
