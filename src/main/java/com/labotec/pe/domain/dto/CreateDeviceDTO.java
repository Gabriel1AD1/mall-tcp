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
public class CreateDeviceDTO {

    @JsonProperty("imei")
    @Schema(description = "IMEI del dispositivo. Debe tener exactamente 15 caracteres.", example = "867869061466660")
    @NotBlank(message = "El IMEI no puede estar vacío")
    @Size(min = 15, max = 15, message = "El IMEI debe tener exactamente 15 caracteres")
    @Pattern(regexp = "\\d+", message = "El IMEI debe contener solo números")
    private String imei;

    @JsonProperty("password")
    @Schema(description = "Contraseña del dispositivo", example = "securePassword123")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @JsonProperty("id_consumer")
    @Schema(description = "ID del consumidor asociado al dispositivo", example = "1001")
    private Long idConsumer;

    @JsonProperty("topic")
    @Schema(description = "Tópico de Kafka asociado al dispositivo", example = "device-topic")
    private String topic;
}
