package com.ms.mail.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.mail.dtos.MailSubscriptionRecordDTO;
import com.ms.mail.services.MailService;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.ms.mail.constants.SubscriptionConstants.*;

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

        JSONObject subscriptionRequest = new JSONObject(message);

        MailSubscriptionRecordDTO mailSubscriptionRecordDTO =
                new MailSubscriptionRecordDTO(
                        subscriptionRequest.getString(USER_EMAIL_ADDRESS_KEY),
                        subscriptionRequest.getString(EMAIL_CONTENT_KEY));

        mailService.sendUserSubscriptionConfirmation(mailSubscriptionRecordDTO);

        System.out.println("Message received: " + message);
    }
}
