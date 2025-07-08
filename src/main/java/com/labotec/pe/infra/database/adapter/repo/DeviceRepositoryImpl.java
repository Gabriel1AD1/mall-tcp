package com.labotec.pe.infra.database.adapter.repo;

import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.output.DeviceRepository;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.infra.database.mapper.DeviceEntityMapper;
import com.labotec.pe.infra.database.repo.DeviceEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {
    private final DeviceEntityRepository deviceEntityRepository;
    private final DeviceEntityMapper deviceEntityMapper;
    @Override
    public Optional<Device> getDeviceByImei(String imei) {
        var deviceEntity = deviceEntityRepository.findByImei(imei);
        return deviceEntity.map(deviceEntityMapper::toDto);
    }

    @Override
    public Device save(Device device) {
        return deviceEntityMapper.toDto(deviceEntityRepository.save(deviceEntityMapper.toEntity(device)));
    }

    @Override
    public void deleteDeviceByImei(String imei) {
        deviceEntityRepository.deleteByImei(imei);
    }

    @Override
    public void updateById(Long id, DeviceStatus deviceStatus) {
        deviceEntityRepository.updateById(id,deviceStatus);
    }

}
