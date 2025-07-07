package com.labotec.pe.app.constants.util;

public class ExtendedDataPacketStatus {

    // Constante para el salto de línea
    private static final String LINE_BREAK = "\r\n";

    // Códigos de respuesta del servidor para el paquete #D# con el salto de línea automáticamente agregado
    public static final String SUCCESS = "#AD#1" + LINE_BREAK;      // Paquete registrado con éxito
    public static final String STRUCTURE_ERROR = "#AD#-1" + LINE_BREAK; // Error en la estructura del paquete
    public static final String INCORRECT_TIME = "#AD#0" + LINE_BREAK; // Tiempo incorrecto
    public static final String ERROR_COORDINATES = "#AD#10" + LINE_BREAK; // Error al recibir las coordenadas
    public static final String ERROR_SPEED_COURSE_ALT = "#AD#11" + LINE_BREAK; // Error al recibir velocidad, rumbo o altitud
    public static final String ERROR_SATS_HDOP = "#AD#12" + LINE_BREAK; // Error al recibir el número de satélites o HDOP
    public static final String ERROR_INPUTS_OUTPUTS = "#AD#13" + LINE_BREAK; // Error al recibir Inputs o Outputs
    public static final String ERROR_ADC = "#AD#14" + LINE_BREAK;   // Error al recibir ADC
    public static final String ERROR_PARAMS = "#AD#15" + LINE_BREAK; // Error al recibir parámetros adicionales
    public static final String CHECKSUM_ERROR = "#AD#16" + LINE_BREAK; // Error en la verificación del checksum

    // Método para obtener el mensaje correspondiente al código de respuesta
    public static String getResponseMessage(String statusCode) {
        return switch (statusCode) {
            case "#AD#1" -> "Paquete registrado con éxito.";
            case "#AD#-1" -> "Error en la estructura del paquete.";
            case "#AD#0" -> "Tiempo incorrecto.";
            case "#AD#10" -> "Error al recibir las coordenadas.";
            case "#AD#11" -> "Error al recibir velocidad, rumbo o altitud.";
            case "#AD#12" -> "Error al recibir el número de satélites o HDOP.";
            case "#AD#13" -> "Error al recibir Inputs o Outputs.";
            case "#AD#14" -> "Error al recibir ADC.";
            case "#AD#15" -> "Error al recibir parámetros adicionales.";
            case "#AD#16" -> "Error en la verificación del checksum.";
            default -> "Código de respuesta desconocido.";
        };
    }
}
