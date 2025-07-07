package com.labotec.pe.app.service;

import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.app.port.input.DeviceRepository;
import com.labotec.pe.app.port.input.annotation.AppService;
import com.labotec.pe.app.port.output.AuthDeviceService;
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
                .map(device -> {
                    AuthDeviceResponse response = authorizeDevice(device, loginWialon);

                    if (StatusLogin.AUTH_SUCCESSFUL.equals(response.getCodeStatus())) {
                        device.setStatus(DeviceStatus.online);
                        deviceRepository.updateById(device.getId(), DeviceStatus.online);
                    }
                    return response;
                })
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
                .topic(EMPTY_STRING)
                .build();
    }

    private AuthDeviceResponse buildNotAuthorizedResponse() {
        return AuthDeviceResponse.builder()
                .codeStatus(StatusLogin.AUTH_NOT_AUTHORIZED)
                .imei(EMPTY_STRING)
                .topic(EMPTY_STRING)
                .build();
    }

    private AuthDeviceResponse buildSuccessfulResponse(Device device) {
        return AuthDeviceResponse.builder()
                .imei(device.getImei())
                .codeStatus(StatusLogin.AUTH_SUCCESSFUL)
                .topic(device.getTopic())
                .build();
    }
}