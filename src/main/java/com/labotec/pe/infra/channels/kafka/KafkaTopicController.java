package com.labotec.pe.infra.channels.kafka;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/kafka/topics")
@Tag(name = "Kafka Topics", description = "Operaciones relacionadas con los tópicos de Kafka")  // Etiqueta para agrupar los endpoints
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaTopicController {

    private final KafkaTopicService kafkaTopicService;

    public KafkaTopicController(KafkaTopicService kafkaTopicService) {
        this.kafkaTopicService = kafkaTopicService;
    }

    @Operation(summary = "Crear un nuevo tópico de Kafka", description = "Crea un tópico en Kafka con el nombre, número de particiones y factor de replicación especificados.")
    @PostMapping("/create")
    public String createTopic(
            @Parameter(description = "Nombre del tópico a crear", required = true) @RequestParam String topicName,
            @Parameter(description = "Número de particiones del tópico", required = true) @RequestParam int partitions,
            @Parameter(description = "Factor de replicación del tópico", required = true) @RequestParam short replicationFactor) {
        return kafkaTopicService.createTopic(topicName, partitions, replicationFactor);
    }

    @Operation(summary = "Eliminar un tópico de Kafka", description = "Elimina el tópico de Kafka especificado.")
    @DeleteMapping("/delete")
    public String deleteTopic(@Parameter(description = "Nombre del tópico a eliminar", required = true) @RequestParam String topicName) {
        return kafkaTopicService.deleteTopic(topicName);
    }

    @Operation(summary = "Listar todos los tópicos de Kafka", description = "Obtiene una lista de todos los tópicos existentes en Kafka.")
    @GetMapping("/list")
    public Set<String> listTopics() {
        return kafkaTopicService.listTopics();
    }

    @Operation(summary = "Describir un tópico de Kafka", description = "Obtiene la descripción detallada de un tópico específico en Kafka.")
    @GetMapping("/describe")
    public String describeTopic(@Parameter(description = "Nombre del tópico a describir", required = true) @RequestParam String topicName) {
        return kafkaTopicService.describeTopic(topicName);
    }
}
