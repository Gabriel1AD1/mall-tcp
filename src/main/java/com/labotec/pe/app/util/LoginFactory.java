package com.labotec.pe.app.util;

import com.labotec.pe.domain.model.LoginWialon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFactory {
    private static final Logger log = LoggerFactory.getLogger(LoginFactory.class);

    private static LoginWialon getLoginWialonV2(String message){
        log.info("mensaje por wialon v2 : {}",message);
        return LoginWialon.builder()
                .imei(ExtractLoginWialonUtils.extractIMEIV2(message))
                .password(ExtractLoginWialonUtils.extractPasswordV2(message))
                .build();
    }
    private static LoginWialon getLoginWialonV1(String message){
        log.info("mensaje por wialon v1 : {}",message);
        String password = ExtractLoginWialonUtils.extractPasswordV1(message);
        log.info("password : {}",password);
        String imei = ExtractLoginWialonUtils.extractIMEIV1(message);
        return LoginWialon.builder()
                .imei(imei)
                .password(password)
                .build();
    }
    public static LoginWialon getLogin(String message) {
        // Comprobamos si el mensaje contiene la versión "2.0"
        if (message.contains("#L#2.0;")) {
            return getLoginWialonV2(message);  // Contiene versión
        } else {
            return getLoginWialonV1(message);  // No contiene versión
        }
    }
}
