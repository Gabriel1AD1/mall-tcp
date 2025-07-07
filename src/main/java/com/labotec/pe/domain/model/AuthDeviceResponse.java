package com.labotec.pe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDeviceResponse {
    private String imei;
    private String codeStatus;
    private String topic;
    private Instant timestamp;

    public static AuthDeviceResponseBuilder builder(){
        return new AuthDeviceResponseBuilder()
                .timestamp(Instant.now())
                .imei("")
                .codeStatus("")
                .topic("");
    }

}
