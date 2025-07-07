package com.labotec.pe.app.util;



import com.labotec.pe.app.constants.util.TypeDataPacket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTypeExtractor {

    // Método para identificar el tipo de dato basado en el prefijo
    public static TypeDataPacket getMessageType(String message) {
        // Expresión regular para capturar el tipo de mensaje
        String regex = "#([A-Za-z]+)#";  // Captura el tipo de dato entre # #

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            // Retorna el tipo de dato (ej: L, D, etc.)

            switch (matcher.group(1)){
                case "L" ->{
                    return TypeDataPacket.LOGIN;
                }
                case "D" ->{
                    return TypeDataPacket.EXTENDED;
                }
                case "SD" ->{
                    return TypeDataPacket.SHORT;
                }
                case "M" ->{
                    return TypeDataPacket.MESSAGE;
                }
            }
        }

        return TypeDataPacket.UNKNOWN;  // Si no se encuentra un tipo válido
    }

}
