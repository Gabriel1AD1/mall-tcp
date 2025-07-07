package com.labotec.pe.infra.channels.kafka;

import org.apache.kafka.clients.admin.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
import java.util.Set;

@Service
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaTopicService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Crear el cliente AdminClient una sola vez
    private AdminClient createAdminClient() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        return AdminClient.create(properties);
    }

    public String createTopic(String topicName, int partitions, short replicationFactor) {
        try (AdminClient adminClient = createAdminClient()) {
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);
            adminClient.createTopics(List.of(newTopic));
            return "Tópico creado exitosamente.";
        } catch (Exception e) {
            return "Error al crear tópico: " + e.getMessage();
        }
    }

    public String deleteTopic(String topicName) {
        try (AdminClient adminClient = createAdminClient()) {
            adminClient.deleteTopics(List.of(topicName));
            return "Tópico eliminado exitosamente.";
        } catch (Exception e) {
            return "Error al eliminar tópico: " + e.getMessage();
        }
    }

    public Set<String> listTopics() {
        try (AdminClient adminClient = createAdminClient()) {
            ListTopicsOptions options = new ListTopicsOptions();
            options.listInternal(true);
            ListTopicsResult result = adminClient.listTopics(options);
            return result.names().get();
        } catch (Exception e) {
            return Set.of("Error al obtener lista de tópicos: " + e.getMessage());
        }
    }

    public String describeTopic(String topicName) {
        try (AdminClient adminClient = createAdminClient()) {
            DescribeTopicsResult result = adminClient.describeTopics(List.of(topicName));
            return result.values().get(topicName).get().toString();
        } catch (Exception e) {
            return "Error al describir tópico: " + e.getMessage();
        }
    }
}
