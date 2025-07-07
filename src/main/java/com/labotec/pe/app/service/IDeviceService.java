package com.labotec.pe.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.input.DeviceRepository;
import com.labotec.pe.app.port.input.annotation.AppService;
import com.labotec.pe.app.port.output.DeviceService;
import com.labotec.pe.domain.dto.CreateDeviceDTO;
import com.labotec.pe.domain.dto.UpdateDeviceDTO;
import com.labotec.pe.domain.entity.Device;

@AppService
@AllArgsConstructor
public class IDeviceService implements DeviceService {
    private final DeviceRepository deviceRepository;
    @Override
    public Device create(CreateDeviceDTO deviceDTO) {
        return deviceRepository.save(Device.builder()
                        .topic(deviceDTO.getTopic())
                        .imei(deviceDTO.getImei())
                        .password(deviceDTO.getPassword())
                .build());
    }

    @Override
    public Device update(UpdateDeviceDTO deviceDTO, String imei) {
        Device device = deviceRepository.getDeviceByImei(imei).orElseThrow(()-> new EntityNotFoundException("El dispositivo no se encuentra "+ imei));
        device.setTopic(deviceDTO.getTopic());
        device.setImei(deviceDTO.getImei());
        device.setPassword(deviceDTO.getPassword());
        return deviceRepository.save(device);}

    @Override
    public void delete(String imei) {
        deviceRepository.deleteDeviceByImei(imei);
    }
}
