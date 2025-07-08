package com.labotec.pe.app.service;

import com.labotec.pe.app.port.output.tcp.TcpDeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.output.DeviceRepository;
import com.labotec.pe.app.port.output.annotation.AppService;
import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.dto.CreateDeviceDTO;
import com.labotec.pe.domain.entity.Device;

@AppService
@AllArgsConstructor
public class IDeviceService implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final TcpDeviceService tcpDeviceService;
    @Override
    public Device create(CreateDeviceDTO deviceDTO) {
        return deviceRepository.save(Device.builder()
                        .imei(deviceDTO.getImei())
                        .password(deviceDTO.getPassword())
                .build());
    }

    @Override
    public void updateStatus(Long id, DeviceStatus deviceStatus) {
        deviceRepository.updateById(id,deviceStatus);
    }

    @Override
    public boolean sendCommand(String imei, String message) {
       return deviceRepository.getDeviceByImei(imei).map(
               device -> tcpDeviceService.sendCommand(device.getImei(), message)
       ).orElse(false);
    }
}
