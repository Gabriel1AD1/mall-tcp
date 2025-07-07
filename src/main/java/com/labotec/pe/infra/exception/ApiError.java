package com.labotec.pe.infra.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ApiError {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;

    // Excluir el campo si es null o vacío
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String debugMessage;

    // Excluir el campo si es null o vacío
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> errors;

    // Constructor personalizado
    public ApiError(HttpStatus status, String message, String debugMessage, Map<String, Object> errors) {
        this.status = status;
        this.timestamp = LocalDateTime.now(); // Asignamos el tiempo actual automáticamente
        this.message = message;
        this.debugMessage = debugMessage;
        this.errors = errors;
    }
}
