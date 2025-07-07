package com.labotec.pe.infra.database.adapter.repo;

import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.input.PositionRepository;
import com.labotec.pe.infra.database.repo.LogsEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PositionRepositoryImpl implements PositionRepository {
    private final LogsEntityRepository logsEntityRepository;
    @Override
    public void saveOrUpdate(String imei, String position) {
        logsEntityRepository.insertUpdate(imei,position);
    }
}
