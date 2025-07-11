package com.labotec.pe.infra.services;

import com.labotec.pe.domain.model.AuthDeviceResponse;

public interface ConnectionsServices {
    void deviceConnected(AuthDeviceResponse authDeviceResponse);
    void deviceDisconnected(AuthDeviceResponse authDeviceResponse);
    void deviceUnknown(AuthDeviceResponse authDeviceResponse);

    void devicesReset();
}
