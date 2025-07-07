package com.labotec.pe.infra.channels.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import com.labotec.pe.domain.model.Position;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, Position> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private ProducerFactory<String, Position> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Dirección de Kafka
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Para serializar JSON
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(configurations());
    }

    private Map<String, Object> configurations() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "localhost:9092"); // Ajusta esto según tu broker
        return configs;
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("nestle-neveras", 3, (short) 1);  // Nombre del tópico, particiones, factor de réplica
    }
}
