package com.ms.event.services;

import com.ms.event.exceptions.EventNotFoundException;
import com.ms.event.exceptions.InvalidOperationException;
import com.ms.event.exceptions.SubscriptionNotAllowedException;
import com.ms.event.exceptions.SubscriptionNotFoundException;
import com.ms.event.models.Event;
import com.ms.event.models.Subscription;
import com.ms.event.repositories.EventRepository;
import com.ms.event.repositories.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackOn = {SubscriptionNotAllowedException.class, EventNotFoundException.class})
public class EventService {

    private final EventRepository eventRepository;
    private final SubscriptionRepository subscriptionRepository;

    public EventService(EventRepository eventRepository, SubscriptionRepository subscriptionRepository) {
        this.eventRepository = eventRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Event get(UUID id){
        return eventRepository.getReferenceById(id);
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public Event save(Event event){

        event.setCreateDate(LocalDateTime.now());
        event.setModifiedDate(LocalDateTime.now());

        return eventRepository.save(event);
    }

    public Event update(Event event){

        if(eventRepository.existsById(event.getId())){
            event.setModifiedDate(LocalDateTime.now());
            return eventRepository.save(event);
        }

        return null;
    }

    public void delete(UUID eventId) throws InvalidOperationException {
        eventRepository.deleteById(eventId);
    }

    public Subscription subscribeParticipant(UUID eventId, String  userEmailAddress) throws EventNotFoundException, SubscriptionNotAllowedException {

        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if(eventOptional.isEmpty())
            throw new EventNotFoundException(String.format("Event %s not found.", eventId));

        Event event = eventOptional.get();
        Subscription subscription = subscriptionRepository.findSubscriptionByEventIdAndUserEmailAddress(eventId, userEmailAddress);

        if(subscription != null)
            throw new SubscriptionNotAllowedException(
                    String.format("There is a subscription for the event [%s] using the same email address [%s]",
                            eventId, userEmailAddress));

        if(exceededSubscriptionLimit(event))
            throw new SubscriptionNotAllowedException("Registration limit has been exceeded");


        subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setUserEmailAddress(userEmailAddress);
        subscription.setSubscribedAt(LocalDateTime.now());
        subscription = subscriptionRepository.save(subscription);

        event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);
        update(event);

        return subscription;

    }

    public void unSubscribeParticipant(UUID eventId, String  userEmailAddress)
            throws EventNotFoundException, SubscriptionNotFoundException {

        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if(eventOptional.isEmpty())
            throw new EventNotFoundException(String.format("Event %s not found.", eventId));

        Event event = eventOptional.get();

        Subscription subscription = subscriptionRepository.findSubscriptionByEventIdAndUserEmailAddress(eventId, userEmailAddress);

        if(subscription == null)
            throw new SubscriptionNotFoundException("Subscription not found");

        event.setRegisteredParticipants(event.getRegisteredParticipants() - 1);
        update(event);
        subscriptionRepository.delete(subscription);

    }

    public boolean exceededSubscriptionLimit(Event event) {
        return event.getRegisteredParticipants() >= event.getMaxParticipants();
    }
}
