package com.labotec.pe.infra.database.repo;

import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.database.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findByImei(String imei);

    void deleteByImei(String imei);


    @Query("update DeviceEntity d set d.status = ?2  where d.id = ?1")
    @Modifying
    @Transactional
    void updateById(Long id, DeviceStatus deviceStatus);


    @Query("update DeviceEntity d set d.status = ?1")
    @Modifying
    @Transactional
    void updateStatus(DeviceStatus deviceStatus);

    boolean existsByImei(String imei);

    Set<DeviceEntity> findAllByStatusIn(Collection<DeviceStatus> statuses);
}