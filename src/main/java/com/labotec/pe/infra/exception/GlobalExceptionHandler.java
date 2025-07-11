package com.labotec.pe.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, HttpServletRequest request) {
        // Mensaje de depuración opcional

        // Crear ApiError
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                "debugMessage",
                Map.of("error", ex.getMessage())
        );

        // Obtener detalles de la solicitud
        String method = request.getMethod(); // Obtener método HTTP
        String endpoint = request.getRequestURI(); // Obtener URI
        String params = request.getParameterMap().toString(); // Convertir parámetros a String
        String pathVariables = ""; // Variables de ruta pueden extraerse del controlador o RequestAttributes
        String body = (String) request.getAttribute("body"); // Obtener cuerpo capturado por el filtro
        String stackTrace = getStackTrace(ex); // Convertir stack trace a String

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleGlobalException(RuntimeException ex, HttpServletRequest request) {
        // Mensaje de depuración opcional

        // Crear ApiError
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                "debugMessage",
                Map.of("error", ex.getMessage())
        );

        // Obtener detalles de la solicitud
        String method = request.getMethod(); // Obtener método HTTP
        String endpoint = request.getRequestURI(); // Obtener URI
        String params = request.getParameterMap().toString(); // Convertir parámetros a String
        String pathVariables = ""; // Variables de ruta pueden extraerse del controlador o RequestAttributes
        String body = (String) request.getAttribute("body"); // Obtener cuerpo capturado por el filtro
        String stackTrace = getStackTrace(ex); // Convertir stack trace a String

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
