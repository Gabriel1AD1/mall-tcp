package com.labotec.pe.app.port.input;

import com.labotec.pe.domain.model.DataPosition;

public interface PositionService {
    DataPosition getDataPacket(Object message, String imei);

}
