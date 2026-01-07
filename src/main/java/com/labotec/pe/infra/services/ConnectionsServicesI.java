package com.labotec.pe.infra.services;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.domain.model.AuthDeviceResponse;
import com.labotec.pe.domain.model.DeviceStatusByKafka;
import com.labotec.pe.infra.channels.kafka.productor.events.KafkaProducerEventsService;
import com.labotec.pe.infra.metrics.TCPMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConnectionsServicesI implements ConnectionsServices {
    private final TCPMetrics tcpMetrics;
  private final KafkaProducerEventsService kafkaProducerEventsService;
    @Override
    public void deviceConnected(AuthDeviceResponse authData) {
        tcpMetrics.incrementTcpConnections();
        kafkaProducerEventsService.sendDeviceEvents(DeviceStatusByKafka.online(authData.getImei(),authData.getId()));
    }

    @Override
    public void deviceDisconnected(AuthDeviceResponse authData) {
        tcpMetrics.decrementTcpConnections();
        kafkaProducerEventsService.sendDeviceEvents(DeviceStatusByKafka.offline(authData.getImei(),authData.getId()));


    }

    @Override
    public void deviceUnknown(AuthDeviceResponse authData) {
        kafkaProducerEventsService.sendDeviceEvents((DeviceStatusByKafka.unknown(authData.getImei(),authData.getId())));

    }

    @Override
    public void devicesReset() {
        kafkaProducerEventsService.sendDeviceEvents(DeviceStatusByKafka.resetDevices());
    }
}
