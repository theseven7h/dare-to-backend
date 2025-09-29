package com.dareTo.services;

import com.dareTo.config.RabbitConfig;
import com.dareTo.data.models.EmailMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EmailPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishEmail(EmailMessage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE,
                    RabbitConfig.ROUTING_KEY,
                    jsonMessage
            );
            System.out.println("Email message published: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Failed to send email message: " + e.getMessage());
            throw new RuntimeException("Failed to send email message", e);
        }
    }
}