package com.labotec.pe.infra.database.adapter.repo;

import com.labotec.pe.domain.enums.DeviceOperationStatus;
import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.output.DeviceRepository;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.infra.database.mapper.DeviceEntityMapper;
import com.labotec.pe.infra.database.repo.DeviceEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void updateById(Long id, DeviceStatus deviceStatus) {
        deviceEntityRepository.updateById(id,deviceStatus);
    }

    @Override
    public boolean existeDeviceByImei(String imei) {
        return deviceEntityRepository.existsByImei(imei);
    }

    @Override
    public Optional<Device> findById(Long id) {
        return deviceEntityRepository.findById(id).map(deviceEntityMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
        deviceEntityRepository.deleteById(id);
    }

    @Override
    public Set<Device> findAllByStatusIn(List<DeviceStatus> status) {
        return deviceEntityRepository.findAllByStatusIn(status).stream().map(deviceEntityMapper::toDto).collect(Collectors.toSet());
    }

}
