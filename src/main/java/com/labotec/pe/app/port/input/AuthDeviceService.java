package com.labotec.pe.app.port.input;


import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.LoginWialon;

public interface AuthDeviceService {
    AuthDeviceResponse getAuthorization(LoginWialon loginWialon);
}
