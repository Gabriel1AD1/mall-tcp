package com.labotec.pe.infra.database.repo;

import com.labotec.pe.infra.database.entity.LogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LogsEntityRepository extends JpaRepository<LogsEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "CALL sp_upsert_logs(:imei, :position)", nativeQuery = true)
    void insertUpdate(
            @Param("imei") String imei,
            @Param("position") String position
    );
}