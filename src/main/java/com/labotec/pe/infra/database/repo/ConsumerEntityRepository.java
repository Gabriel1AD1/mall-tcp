package com.labotec.pe.infra.database.repo;

import com.labotec.pe.infra.database.entity.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerEntityRepository extends JpaRepository<ConsumerEntity, Long> {
}