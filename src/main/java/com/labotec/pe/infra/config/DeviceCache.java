package com.labotec.pe.infra.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.pe.domain.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCache {
  private Long id;
  private String imei;
  private String password;
  private DeviceStatus status;


}
