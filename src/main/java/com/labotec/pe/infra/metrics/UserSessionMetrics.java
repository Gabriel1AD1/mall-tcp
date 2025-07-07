package com.labotec.pe.infra.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserSessionMetrics {

    private final AtomicLong activeSessions;

    @Autowired
    public UserSessionMetrics(MeterRegistry meterRegistry) {
        this.activeSessions = new AtomicLong(0);
        meterRegistry.gauge("active_sessions", activeSessions); // Definir el gauge
    }

    public void incrementActiveSessions() {
        activeSessions.incrementAndGet();  // Incrementar el número de sesiones activas
    }

    public void decrementActiveSessions() {
        activeSessions.decrementAndGet();  // Decrementar el número de sesiones activas
    }
}
