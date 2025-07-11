package com.labotec.pe.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceStatusByKafka {
    @JsonProperty("deviceId")
    private Long deviceId;
    @JsonProperty("resetDevices")
    private Boolean resetDevices;
    private String imei;
    private DeviceStatus status;

    public static DeviceStatusByKafka resetDevices() {
        return DeviceStatusByKafka.builder()
                .deviceId(0L)
                .imei("all")
                .status(DeviceStatus.offline)
                .resetDevices(true)
                .build();
    }

    public static DeviceStatusByKafka online(String imei,Long id){
        return DeviceStatusByKafka.builder()
                .deviceId(id)
                .resetDevices(false)
                .imei(imei)
                .status(DeviceStatus.online)
                .build();
    }
    public static DeviceStatusByKafka offline(String imei,Long id){
        return DeviceStatusByKafka.builder()
                .deviceId(id)
                .resetDevices(false)
                .imei(imei)
                .status(DeviceStatus.offline)
                .build();
    }
    public static DeviceStatusByKafka unknown(String imei,Long id){
        return DeviceStatusByKafka.builder()
                .deviceId(id)
                .resetDevices(false)
                .imei(imei)
                .status(DeviceStatus.unknown)
                .build();
    }
}
