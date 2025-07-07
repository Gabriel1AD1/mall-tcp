package com.labotec.pe.infra.factory;

import com.labotec.pe.app.constants.server.ProfileServerTcp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TCPServerHandlerFactory {

    private final Map<String, TcpProfileHandler> handlers;

    @Autowired
    public TCPServerHandlerFactory(
            @Qualifier("NO_AUTH") TcpProfileHandler noAuthHandler,
            @Autowired(required = false) @Qualifier("PRODUCTION_KAFKA") TcpProfileHandler productionKafkaHandler,
            @Qualifier("TEST") TcpProfileHandler testHandler,
            @Qualifier("PRODUCTION_REST") TcpProfileHandler productionRest ) {

        this.handlers = Map.of(
                "NO_AUTH", noAuthHandler,
                "PRODUCTION_KAFKA", productionKafkaHandler != null ? productionKafkaHandler : testHandler, // Si es null, usa TEST
                "TEST", testHandler,
                "PRODUCTION_REST",productionRest
        );
    }


    public TcpProfileHandler getHandler(ProfileServerTcp profile) {
        return handlers.getOrDefault(profile.name(), handlers.get("TEST")); // Default a TEST si no existe
    }
}
