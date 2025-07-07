package com.labotec.pe.infra.channels.kafka;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/kafka")
@AllArgsConstructor
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaTopicViewController {

    private final KafkaTopicService kafkaTopicService;
    private final KafkaLogConsumerService kafkaLogConsumerService;


    // Vista principal con la lista de tópicos
    @GetMapping("/topics")
    public String listTopics(Model model) {
        Set<String> topics = kafkaTopicService.listTopics();
        model.addAttribute("topics", topics);
        return "topic-list";
    }

    // Vista para crear un tópico
    @GetMapping("/create-topic")
    public String showCreateTopicForm(Model model) {
        model.addAttribute("topicName", "");
        model.addAttribute("partitions", 1);
        model.addAttribute("replicationFactor", (short) 1);
        return "create-topic.html";
    }

    // Acción para crear un tópico
    @PostMapping("/create-topic")
    public String createTopic(@RequestParam String topicName,
                              @RequestParam int partitions,
                              @RequestParam short replicationFactor,
                              Model model) {
        String result = kafkaTopicService.createTopic(topicName, partitions, replicationFactor);
        model.addAttribute("message", result);
        return "create-topic";
    }

    // Acción para eliminar un tópico
    @PostMapping("/delete-topic")
    public String deleteTopic(@RequestParam String topicName, Model model) {
        String result = kafkaTopicService.deleteTopic(topicName);
        model.addAttribute("message", result);
        return "redirect:/kafka/topics";
    }

    // Vista para describir un tópico
    @GetMapping("/describe-topic/{topicName}")
    public String describeTopic(@PathVariable String topicName, Model model) {
        String description = kafkaTopicService.describeTopic(topicName);
        model.addAttribute("topicDescription", description);
        return "describe-topic";
    }

    @GetMapping("/logs/{topicName}")
    public String viewLogs(@PathVariable String topicName, Model model) {
        // Llamamos al servicio para obtener los últimos logs
        String logs = kafkaLogConsumerService.getLastLogsForTopic(topicName); // Obtener los últimos logs
        model.addAttribute("topicName", topicName);
        model.addAttribute("logs", logs);
        return "view-logs";
    }
}
