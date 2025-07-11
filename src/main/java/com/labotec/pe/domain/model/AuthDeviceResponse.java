package com.labotec.pe.domain.model;

import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDeviceResponse {
    private Long id;
    private String imei;
    private DeviceStatus deviceStatus;
    private String codeStatus;
    private Instant timestamp;

    public static AuthDeviceResponseBuilder builder(){
        return new AuthDeviceResponseBuilder()
                .id(1L)
                .deviceStatus(DeviceStatus.offline)
                .timestamp(Instant.now())
                .imei("")
                .codeStatus("");
    }

    public static AuthDeviceResponse of(String imei) {
        return AuthDeviceResponse.builder()
                .imei(imei)
                .codeStatus("")
                .timestamp(Instant.now())
                .build();
    }
    public void online(){
        this.deviceStatus = DeviceStatus.online;
        this.timestamp = Instant.now();
    }
    public void unknown(){
        this.deviceStatus = DeviceStatus.unknown;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthDeviceResponse that)) return false;

        return imei != null && imei.equals(that.imei);
    }

    @Override
    public int hashCode() {
        return imei != null ? imei.hashCode() : 0;
    }

}
