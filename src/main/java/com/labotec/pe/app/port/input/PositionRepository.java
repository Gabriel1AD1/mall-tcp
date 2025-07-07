package com.labotec.pe.app.port.input;

public interface PositionRepository {
    void saveOrUpdate(String imei,String position);
}
