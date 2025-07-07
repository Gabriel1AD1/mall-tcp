package com.labotec.pe.app.port.output;

import com.labotec.pe.domain.model.DataPosition;

public interface PositionService {
    DataPosition getDataPacket(Object message, String imei);

}
