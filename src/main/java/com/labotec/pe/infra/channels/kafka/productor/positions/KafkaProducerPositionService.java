package com.labotec.pe.infra.channels.kafka.productor.positions;

import com.labotec.pe.app.util.JsonUtil;
import com.labotec.pe.domain.model.Position;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaProducerPositionService {
    @Value("${kafka.topic.position}")
    private String topic;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerPositionService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPosition(Position position) {
        kafkaTemplate.send(topic, JsonUtil.toJson(position));
        log.info("Mensaje enviado al tópico: {} con contenido: {}", topic, position);
    }
}
