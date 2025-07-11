package com.labotec.pe.app.service;

import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.app.port.output.DeviceRepository;
import com.labotec.pe.app.port.output.annotation.AppService;
import com.labotec.pe.app.port.input.AuthDeviceService;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.LoginWialon;

import static com.labotec.pe.app.constants.service.AuthDeviceConstant.EMPTY_STRING;

@AllArgsConstructor
@AppService
public class IAuthDeviceService implements AuthDeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public AuthDeviceResponse getAuthorization(LoginWialon loginWialon) {
        return deviceRepository.getDeviceByImei(loginWialon.getImei())
                .map(device -> authorizeDevice(device, loginWialon))
                .orElseGet(this::buildAuthFailedResponse);
    }

    private AuthDeviceResponse authorizeDevice(Device device, LoginWialon loginWialon) {
        if (!device.getPassword().equals(loginWialon.getPassword())) {
            return buildNotAuthorizedResponse();
        }
        return buildSuccessfulResponse(device);
    }

    private AuthDeviceResponse buildAuthFailedResponse() {
        return AuthDeviceResponse.builder()
                .imei(EMPTY_STRING)
                .codeStatus(StatusLogin.AUTH_FAILED)
                .build();
    }

    private AuthDeviceResponse buildNotAuthorizedResponse() {
        return AuthDeviceResponse.builder()
                .codeStatus(StatusLogin.AUTH_NOT_AUTHORIZED)
                .imei(EMPTY_STRING)
                .build();
    }

    private AuthDeviceResponse buildSuccessfulResponse(Device device) {
        return AuthDeviceResponse.builder()
                .id(device.getId())
                .deviceStatus(DeviceStatus.online)
                .imei(device.getImei())
                .codeStatus(StatusLogin.AUTH_SUCCESSFUL)
                .build();
    }
}