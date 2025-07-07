package com.labotec.pe.app.port.output;

import com.labotec.pe.domain.dto.CreateDeviceDTO;
import com.labotec.pe.domain.dto.UpdateDeviceDTO;
import com.labotec.pe.domain.entity.Device;

public interface DeviceService {
    Device create(CreateDeviceDTO deviceDTO);

    Device update(UpdateDeviceDTO deviceDTO, String imei);

    void delete(String imei);

}
