package com.labotec.pe.app.port.output;

import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;

import java.util.Optional;

public interface DeviceRepository {
    Optional<Device>getDeviceByImei(String imi);

    Device save(Device device);

    void deleteDeviceByImei(String imei);

    void updateById(Long id, DeviceStatus deviceStatus);
}
