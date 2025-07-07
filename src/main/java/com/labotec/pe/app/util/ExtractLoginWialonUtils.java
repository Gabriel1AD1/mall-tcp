package com.labotec.pe.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ExtractLoginWialonUtils {
    private static final Logger log = LoggerFactory.getLogger(ExtractLoginWialonUtils.class);

    // Método para limpiar el mensaje antes de procesarlo
    private static String cleanMessage(String message) {
        if (message != null) {
            return message.replaceAll("[\\r\\n\\t]", "").trim();
        }
        return null;
    }

    public static String extractIMEIV2(String message) {
        message = cleanMessage(message);
        String[] parts = message.split(";");
        log.info("Imei v2 : {}", parts.length > 1 ? parts[1] : "No encontrado");
        return parts.length > 1 ? parts[1] : null;
    }

    public static String extractPasswordV2(String message) {
        message = cleanMessage(message);
        String[] parts = message.split(";");
        if (parts.length > 2) {
            String password = parts[2].trim();
            if (Objects.equals(password, "NA")) {
                password = "";
            }
            log.info("Password v2 : {}", password);
            return password;
        }
        return null;
    }

    public static String extractIMEIV1(String message) {
        message = cleanMessage(message);
        if (message != null && message.startsWith("#L#")) {
            String[] parts = message.split(";");
            if (parts.length > 0) {
                String imei = parts[0].substring(3).trim(); // Remueve "#L#" del IMEI
                log.info("IMEI v1 : {}", imei);
                return imei;
            }
        }
        return null;
    }

    public static String extractPasswordV1(String message) {
        message = cleanMessage(message);
        if (message != null && message.startsWith("#L#")) {
            String[] parts = message.split(";");
            if (parts.length > 1) {
                String password = parts[1].trim();
                if (Objects.equals(password, "NA")) {
                    password = "";
                }
                log.info("Password v1 : {}", password);
                return password;
            }
        }
        return null;
    }

}
