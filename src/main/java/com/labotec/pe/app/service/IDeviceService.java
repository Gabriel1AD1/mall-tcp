package com.labotec.pe.app.service;

import com.labotec.pe.app.port.output.tcp.TcpDeviceService;
import com.labotec.pe.domain.enums.DeviceOperationStatus;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.domain.model.DeviceResponse;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.output.DeviceRepository;
import com.labotec.pe.app.port.output.annotation.AppService;
import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.dto.RequestDeviceDTO;
import com.labotec.pe.domain.entity.Device;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AppService
@AllArgsConstructor
public class IDeviceService implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final TcpDeviceService tcpDeviceService;



    @Override
    public DeviceResponse create(RequestDeviceDTO deviceDTO) {
        try{
            if (deviceRepository.existeDeviceByImei(deviceDTO.getImei())) {
                return DeviceResponse.imeiExist(deviceDTO.getImei());
            }
            deviceRepository.save(Device.builder()
                    .id(deviceDTO.getId())
                    .imei(deviceDTO.getImei())
                    .password(deviceDTO.getPassword())
                            .status(DeviceStatus.offline)
                    .build());
            return DeviceResponse.ok();
        } catch (Exception e) {
            return DeviceResponse.error(e.getMessage());
        }

    }

    @Override
    public DeviceResponse update(Long id, RequestDeviceDTO deviceDTO) {
        try {
            Optional<Device> optionalDevice = deviceRepository.findById(id);

            if (optionalDevice.isEmpty()) {
                return DeviceResponse.deviceNotFound(id);
            }

            Device deviceEntity = optionalDevice.get();

            if (!deviceEntity.getImei().equals(deviceDTO.getImei())) {
                boolean imeiExiste = deviceRepository.existeDeviceByImei(deviceDTO.getImei());
                if (imeiExiste) {
                    return DeviceResponse.imeiExist(deviceDTO.getImei());
                }
            }

            // Actualizar campos
            deviceEntity.setImei(deviceDTO.getImei());
            deviceEntity.setPassword(deviceDTO.getPassword());
            // Guardar cambios
            deviceRepository.save(deviceEntity);

            return DeviceResponse.ok();
        }catch (Exception e) {
            return DeviceResponse.error(e.getMessage());
        }

    }


    @Override
    public DeviceResponse delete(Long id) {
        try {
            Optional<Device> optionalDevice = deviceRepository.findById(id);
            if (optionalDevice.isEmpty()) {
                return DeviceResponse.deviceNotFound(id);
            }
            deviceRepository.deleteById(id);
            return DeviceResponse.ok();
        } catch (Exception e) {
            return DeviceResponse.error(e.getMessage());
        }

    }

    @Override
    public Set<Device> getAllDevices(List<DeviceStatus> status) {
        return deviceRepository.findAllByStatusIn(status);
    }


    @Override
    public boolean sendCommand(String imei, String message) {
       return deviceRepository.getDeviceByImei(imei).map(
               device -> tcpDeviceService.sendCommand(device.getImei(), message)
       ).orElse(false);
    }

    @Override
    public void updateStatusById(Long id, DeviceStatus deviceStatus) {
        deviceRepository.updateById(id, deviceStatus);
    }
}
