package com.labotec.pe.app.constants.util;

public final class StatusLogin {

    // Constante para el salto de línea
    public static final String LINE_BREAK = "\r\n";

    // Códigos de respuesta con el salto de línea automáticamente agregado
    public static final String AUTH_SUCCESSFUL = "#AL#1" + LINE_BREAK;               // Autorización exitosa
    public static final String AUTH_FAILED = "#AL#0" + LINE_BREAK;     // Conexión rechazada
    public static final String AUTH_NOT_AUTHORIZED = "#AL#01" + LINE_BREAK;   // Error en la verificación de la contraseña
    public static final String CHECKSUM_ERROR = "#AL#10" + LINE_BREAK;   // Error en la verificación del checksum
    public static final String PASSWORD_OK = "1234"; // Contraseña correcta

    // Descripción de los códigos
    public static String getDescription(String code) {
        return switch (code) {
            case "#AL#1" -> "Unidad autorizada correctamente.";
            case "#AL#0" -> "Conexión rechazada. Posibles razones: versión incorrecta del protocolo, dispositivo no creado en el servidor o estructura incorrecta del paquete.";
            case "#AL#01" -> "Error de verificación de contraseña.";
            case "#AL#10" -> "Error de verificación del checksum.";
            default -> "Error desconocido.";
        };
    }

    // Método para verificar CRC16 (esto debe implementarse dependiendo de la lógica de verificación de CRC)
    public static boolean verifyCRC16(String packet, String expectedCRC) {
        // Este método debe contener la lógica para calcular y verificar el CRC16
        // Para simplificar, se supone que el CRC se obtiene de algún modo del paquete
        String calculatedCRC = calculateCRC16(packet);
        return expectedCRC.equals(calculatedCRC);
    }

    // Método simulado para calcular CRC16 (reemplazar con la lógica real)
    private static String calculateCRC16(String packet) {
        // Aquí iría el código para calcular el CRC16 basado en el paquete
        // Retornamos un valor simulado por ahora
        return "ABCD";
    }
}
