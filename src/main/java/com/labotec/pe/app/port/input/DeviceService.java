package com.labotec.pe.app.port.input;

import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;

import java.util.List;
import java.util.Set;

public interface DeviceService {

  Set<Device> getAllDevices(List<DeviceStatus> status);
    boolean sendCommand(String imei, String message);

}
