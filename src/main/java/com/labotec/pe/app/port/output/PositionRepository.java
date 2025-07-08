package com.labotec.pe.app.port.output;

public interface PositionRepository {
    void saveOrUpdate(String imei,String position);
}
