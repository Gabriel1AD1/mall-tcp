package com.labotec.pe.app.constants.service;

public enum DataPacketError {
    SUCCESS,
    STRUCTURE_ERROR,
    INCORRECT_TIME,
    ERROR_COORDINATES,
    ERROR_SPEED_COURSE_ALT,
    ERROR_SATS_HDOP,
    ERROR_INPUTS_OUTPUTS,
    ERROR_ADC,
    ERROR_PARAMS,
    CHECKSUM_ERROR,
    ERROR_SATS,
    SHORT_CHECKSUM_ERROR;

    public static String getResponseMessage(DataPacketError error) {
        return switch (error) {
            case SUCCESS -> "Paquete registrado con éxito.";
            case STRUCTURE_ERROR -> "Error en la estructura del paquete.";
            case INCORRECT_TIME -> "Tiempo incorrecto.";
            case ERROR_COORDINATES -> "Error al recibir las coordenadas.";
            case ERROR_SPEED_COURSE_ALT -> "Error al recibir velocidad, rumbo o altitud.";
            case ERROR_SATS_HDOP -> "Error al recibir el número de satélites o HDOP.";
            case ERROR_INPUTS_OUTPUTS -> "Error al recibir Inputs o Outputs.";
            case ERROR_ADC -> "Error al recibir ADC.";
            case ERROR_PARAMS -> "Error al recibir parámetros adicionales.";
            case CHECKSUM_ERROR -> "Error en la verificación del checksum.";
            case ERROR_SATS -> "Error al recibir número de satélites.";
            case SHORT_CHECKSUM_ERROR -> "Error en la verificación del checksum para paquete corto.";
            default -> "Código de respuesta desconocido.";
        };
    }
}
