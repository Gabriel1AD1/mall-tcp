package com.labotec.pe.infra.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TcpRequestsMetrics {

    private final Counter tcpRequestsCounter;

    @Autowired
    public TcpRequestsMetrics(MeterRegistry meterRegistry) {
        this.tcpRequestsCounter = meterRegistry.counter("tcp_requests_total");
    }

    public void incrementTcpRequests() {
        tcpRequestsCounter.increment();
    }

    public double getTcpRequestCount() {
        return tcpRequestsCounter.count();
    }
}