package com.labotec.pe.domain.model;

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
    private String codeStatus;
    private Instant timestamp;

    public static AuthDeviceResponseBuilder builder(){
        return new AuthDeviceResponseBuilder()
                .id(1L)
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
