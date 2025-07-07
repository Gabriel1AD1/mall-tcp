package com.labotec.pe.infra.channels.kafka;

import lombok.AllArgsConstructor;
import com.labotec.pe.domain.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, Position> kafkaTemplate;

    public void sendPosition(String topic ,Position position) {
        kafkaTemplate.send(topic, position);
        log.info("Mensaje enviado al tópico: {} con contenido: {}", topic, position);
    }
}
