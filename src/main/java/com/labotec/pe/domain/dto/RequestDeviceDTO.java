package com.labotec.pe.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "DTO para la creación de un dispositivo")
public class RequestDeviceDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("imei")
    @NotBlank(message = "El IMEI no puede estar vacío")

    private String imei;

    @JsonProperty("password")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

}
