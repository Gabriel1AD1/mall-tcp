package com.labotec.pe.app.port.input;

import com.labotec.pe.domain.dto.CreateDeviceDTO;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;

public interface DeviceService {
    Device create(CreateDeviceDTO deviceDTO);

    void updateStatus(Long id, DeviceStatus deviceStatus);

    boolean sendCommand(String imei, String message);
}
