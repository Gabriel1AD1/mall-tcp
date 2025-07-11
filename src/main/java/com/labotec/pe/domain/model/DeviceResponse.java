package com.labotec.pe.domain.model;

import com.labotec.pe.domain.enums.DeviceOperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponse {
    private Long id;

    private String imei;
    private DeviceOperationStatus status;
    private String message;

    public static DeviceResponse ok() {
        return new DeviceResponse(0L, "", DeviceOperationStatus.OK, "");
    }

    public static DeviceResponse error(String message) {
        return new DeviceResponse(0L, "", DeviceOperationStatus.ERROR, message);
    }

    public static DeviceResponse imeiExist(String imei) {
        return new DeviceResponse(0L, imei, DeviceOperationStatus.IMEI_EXIST, "imei " + imei + " already exists");
    }

    public static DeviceResponse deviceNotFound(Long id) {
        return new DeviceResponse(id, "", DeviceOperationStatus.DEVICE_NOT_FOUND, "device with id " + id + " not found");
    }


}
