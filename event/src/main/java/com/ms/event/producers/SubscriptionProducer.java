package com.ms.event.producers;

import com.ms.event.models.Event;
import com.ms.event.models.Subscription;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.ms.event.constants.SubscriptionConstants.*;

@Service
public class SubscriptionProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public SubscriptionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Subscription subscription) {

        JSONObject subscriptionObject = new JSONObject()
                .put(USER_EMAIL_ADDRESS_KEY, subscription.getUserEmailAddress())
                .put(EMAIL_CONTENT_KEY, buildTextMessage(subscription));

        kafkaTemplate.send("subscription-request", subscriptionObject.toString());
    }

    private String buildTextMessage(Subscription subscription) {


        Event event = subscription.getEvent();

        return String.format(
                "Hi, this is an automatic mailAddress created for educational purposes.\n" +
                "Your subscription to event %s has been confirmed.\n" +
                "Here are some details about the event:\n" +
                "Event Description: %s \n" +
                "Event Date: %s \n" +
                "Subscribed at: %s \n" +
                "This message was possible to be sent due the use of the Spring Framework and the Apache Kafka built in a microservice architectural approach.\n" +
                "Best regards, Matheus",
                event.getTitle(),
                event.getDescription(),
                event.getEventDate(),
                subscription.getSubscribedAt()
        );
    }
}


