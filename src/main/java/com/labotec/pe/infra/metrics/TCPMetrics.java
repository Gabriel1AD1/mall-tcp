package com.labotec.pe.infra.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TCPMetrics {

    private final TcpConnectionsMetrics tcpConnectionsMetrics;
    private final TcpRequestsMetrics tcpRequestsMetrics;
    private final UserSessionMetrics userSessionMetrics;
    @Autowired
    public TCPMetrics(TcpConnectionsMetrics tcpConnectionsMetrics,
                      TcpRequestsMetrics tcpRequestsMetrics,
         UserSessionMetrics userSessionMetrics) {
        this.tcpConnectionsMetrics = tcpConnectionsMetrics;
        this.tcpRequestsMetrics = tcpRequestsMetrics;
        this.userSessionMetrics = userSessionMetrics;
    }

    public void incrementTcpConnections() {
        tcpConnectionsMetrics.incrementTcpConnections();
    }

    public void decrementTcpConnections() {
        tcpConnectionsMetrics.decrementTcpConnections();
    }

    public void incrementTcpRequests() {
        tcpRequestsMetrics.incrementTcpRequests();
    }


    public void activeSession() {
        userSessionMetrics.incrementActiveSessions();
    }
    public void desactiveSession() {
        userSessionMetrics.decrementActiveSessions();
    }
}
