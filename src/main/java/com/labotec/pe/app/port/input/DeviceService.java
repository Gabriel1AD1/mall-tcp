package com.labotec.pe.app.port.input;

import com.labotec.pe.domain.dto.RequestDeviceDTO;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceOperationStatus;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.domain.model.DeviceResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface DeviceService {
    @Transactional
    DeviceResponse create(RequestDeviceDTO deviceDTO);
    @Transactional
    DeviceResponse update(Long id, RequestDeviceDTO deviceDTO);
    @Transactional
    DeviceResponse delete(Long id);
    Set<Device> getAllDevices(List<DeviceStatus> status);
    boolean sendCommand(String imei, String message);

    void updateStatusById(Long id, DeviceStatus deviceStatus);
}
