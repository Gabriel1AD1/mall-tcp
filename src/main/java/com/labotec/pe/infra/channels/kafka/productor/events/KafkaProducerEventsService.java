package com.labotec.pe.infra.channels.kafka.productor.events;

import com.labotec.pe.app.util.JsonUtil;
import com.labotec.pe.domain.model.DeviceStatusByKafka;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerEventsService {
    @Value("${kafka.topic.devices}")
    private String topic;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerEventsService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendDeviceEvents(DeviceStatusByKafka message) {
        kafkaTemplate.send(topic, JsonUtil.toJson(message));
        log.info("Sent message to Kafka topic {}: {}", "sendDeviceEvents", message);
    }
}
