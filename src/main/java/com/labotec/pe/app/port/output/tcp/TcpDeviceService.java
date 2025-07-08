package com.labotec.pe.app.port.output.tcp;

public interface TcpDeviceService {
    boolean sendCommand(String imei, String command);
}
