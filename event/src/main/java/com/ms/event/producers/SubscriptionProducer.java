package com.ms.event.producers;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.ms.event.models.Subscription;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubscriptionProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public SubscriptionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Subscription subscription) {

        Map<String, String> subscriptionProperties = new HashMap<>();

        subscriptionProperties.put("event-name", subscription.getEvent().getTitle());
        subscriptionProperties.put("event-description", subscription.getEvent().getDescription());
        subscriptionProperties.put("event-date", subscription.getEvent().getEventDate().toString());
        subscriptionProperties.put("user-email-address", subscription.getUserEmailAddress());
        subscriptionProperties.put("subscribed-at", subscription.getSubscribedAt().toString());


        kafkaTemplate.send("subscription-request", subscriptionProperties.toString());
    }
}


