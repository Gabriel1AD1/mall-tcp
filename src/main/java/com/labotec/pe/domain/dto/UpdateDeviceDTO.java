package com.labotec.pe.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateDeviceDTO {
    @JsonProperty("imei")
    private String imei;
    @JsonProperty("password")
    private String password;
    @JsonProperty("id_consumer")
    private Long idConsumer;
    @JsonProperty("topic")
    private String topic;

}
