package com.labotec.pe.domain.entity;

import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.database.entity.DeviceEntity;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link DeviceEntity}
 */
@AllArgsConstructor
@Getter
@ToString
@Builder
@Setter
public class Device implements Serializable {
    private  Long id;
    private  String imei;
    private  String password;
    private DeviceStatus status;
    private String topic;
}