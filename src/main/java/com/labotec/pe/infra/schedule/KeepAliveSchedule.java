package com.labotec.pe.infra.schedule;

import com.labotec.pe.app.port.output.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeepAliveSchedule {
    private final DeviceService deviceService;



}
