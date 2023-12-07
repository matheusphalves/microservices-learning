package com.ms.event.consumers;

import com.ms.event.services.EventService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionConsumer {
    private EventService eventService;

    public SubscriptionConsumer(EventService eventService) {
        this.eventService = eventService;
    }

    @KafkaListener(topics = "subscription-request", groupId = "default-group")
    public void receiveMessage(String message){
        System.out.println("Message received: " + message);

//        eventService.subscribeParticipant();
    }
}
