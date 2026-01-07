package com.labotec.pe.app.port.output;

import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DeviceRepository {
    Optional<Device>getDeviceByImei(String imi);

  void updateById(Long id, DeviceStatus deviceStatus);

  Set<Device> findAllByStatusIn(List<DeviceStatus> status);
}
