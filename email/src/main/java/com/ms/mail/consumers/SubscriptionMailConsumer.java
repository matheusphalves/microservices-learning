package com.ms.mail.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.mail.services.MailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionMailConsumer {

    private MailService mailService;
    private KafkaTemplate<String, String> kafkaTemplate;

    public SubscriptionMailConsumer(
            KafkaTemplate<String, String> kafkaTemplate, MailService mailService) {
        this.kafkaTemplate = kafkaTemplate;
        this.mailService = mailService;
    }
    @KafkaListener(topics = "subscription-request", groupId = "group_id")
    public void receiveMessage(String message) throws JsonProcessingException {

        System.out.println("Message received: " + message);
    }
}
