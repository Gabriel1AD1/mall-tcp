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
    private final DeviceService deviceService;
    private final KafkaProducerEventsService kafkaProducerEventsService;
    @Override
    public void deviceConnected(AuthDeviceResponse authData) {
        tcpMetrics.incrementTcpConnections();
        deviceService.updateStatusById(authData.getId(), DeviceStatus.online);
        kafkaProducerEventsService.sendDeviceEvents(DeviceStatusByKafka.online(authData.getImei(),authData.getId()));
    }

    @Override
    public void deviceDisconnected(AuthDeviceResponse authData) {
        tcpMetrics.decrementTcpConnections();
        deviceService.updateStatusById(authData.getId(), DeviceStatus.offline);
        kafkaProducerEventsService.sendDeviceEvents(DeviceStatusByKafka.offline(authData.getImei(),authData.getId()));


    }

    @Override
    public void deviceUnknown(AuthDeviceResponse authData) {
        deviceService.updateStatusById(authData.getId(), DeviceStatus.unknown);
        kafkaProducerEventsService.sendDeviceEvents((DeviceStatusByKafka.unknown(authData.getImei(),authData.getId())));

    }

    @Override
    public void devicesReset() {
        kafkaProducerEventsService.sendDeviceEvents(DeviceStatusByKafka.resetDevices());
    }
}
