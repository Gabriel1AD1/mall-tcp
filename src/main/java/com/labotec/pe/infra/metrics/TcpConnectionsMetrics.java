package com.labotec.pe.infra.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TcpConnectionsMetrics {

    private final MeterRegistry meterRegistry;
    private final Counter tcpConnectionsCounter;

    @Autowired
    public TcpConnectionsMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.tcpConnectionsCounter = meterRegistry.counter("tcp_connections_total");
    }

    public void incrementTcpConnections() {
        tcpConnectionsCounter.increment();
    }

    public void decrementTcpConnections() {
        meterRegistry.counter("tcp_connections_total").increment(-1);
    }
}
