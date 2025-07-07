package com.labotec.pe.infra.channels.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaLogConsumerService {

    private final String bootstrapServers = "localhost:9092"; // Ajusta la URL según tu configuración
    private final String groupId = "log-consumer-group"; // Grupo de consumidores

    public String getLastLogsForTopic(String topicName) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", groupId);
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("auto.offset.reset", "latest"); // Para leer solo los mensajes más recientes

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topicName));

        StringBuilder logs = new StringBuilder();
        try {
            // Obtenemos los mensajes más recientes
            var records = consumer.poll(Duration.ofMillis(1000)); // Espera un segundo para obtener los mensajes
            records.forEach(record -> {
                logs.append(record.value()).append("\n");
            });
        } catch (Exception e) {
            logs.append("Error al obtener los logs: ").append(e.getMessage());
        } finally {
            consumer.close();
        }

        return logs.toString();
    }
}
