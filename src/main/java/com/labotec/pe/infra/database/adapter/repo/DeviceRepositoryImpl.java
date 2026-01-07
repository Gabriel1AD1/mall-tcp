package com.labotec.pe.infra.database.adapter.repo;

import com.labotec.pe.app.port.output.DeviceRepository;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.config.CacheDao;
import com.labotec.pe.infra.config.DeviceCache;
import com.labotec.pe.infra.database.mapper.DeviceEntityMapper;
import com.labotec.pe.infra.database.repo.DeviceEntityRepository;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {

  private final DeviceEntityRepository deviceEntityRepository;

  private final DeviceEntityMapper deviceEntityMapper;
  public static final String key = "device:cache:";
  public static final String keyImei = "device:cache:imei:";
  private final CacheDao<DeviceCache> cacheDao;

  @Override
  public Optional<Device> getDeviceByImei(String imei) {
    Optional<DeviceCache> deviceEntity =
        Optional.ofNullable(cacheDao.get(keyImei.concat(imei), DeviceCache.class));

    return deviceEntity.map(deviceEntityMapper::toModelDomain);
  }

  @Override
  public void updateById(Long id, DeviceStatus deviceStatus) {
    deviceEntityRepository.updateById(id, deviceStatus);
  }

  @Override
  public Set<Device> findAllByStatusIn(List<DeviceStatus> status) {
    return ActiveChannelsRegistry.getAllDevices().stream()
        .map(
            device ->
                Device.builder()
                    .status(device.getDeviceStatus())
                    .id(device.getId())
                    .imei(device.getImei())
                    .password("Sin asignar")
                    .build())
        .collect(Collectors.toSet());
  }
}
