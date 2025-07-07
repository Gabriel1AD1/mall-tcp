package com.labotec.pe.app.port.output;


import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.LoginWialon;

public interface AuthDeviceService {
    AuthDeviceResponse getAuthorization(LoginWialon loginWialon);
}
