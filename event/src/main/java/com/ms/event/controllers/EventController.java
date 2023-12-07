package com.ms.event.controllers;

import com.ms.event.dtos.EventRecordDTO;
import com.ms.event.dtos.SubscriptionRequestDTO;
import com.ms.event.exceptions.EventNotFoundException;
import com.ms.event.exceptions.InvalidOperationException;
import com.ms.event.exceptions.SubscriptionNotAllowedException;
import com.ms.event.exceptions.SubscriptionNotFoundException;
import com.ms.event.models.Event;
import com.ms.event.models.Subscription;
import com.ms.event.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("events/")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRecordDTO eventRecordDTO) {

        Event event = new Event();
        BeanUtils.copyProperties(eventRecordDTO, event);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.save(event));
    }

    @GetMapping
    public List<Event> getAllEvents(){
        return eventService.findAll();
    }

    @GetMapping("/{eventId}")
    public Event get(@PathVariable UUID eventId) throws EventNotFoundException {
        return eventService.get(eventId);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID eventId) throws InvalidOperationException {
        eventService.delete(eventId);
    }

    @PostMapping("/{eventId}/subscribe")
    public ResponseEntity<Subscription> subscribeUser(
            @PathVariable UUID eventId, @Valid @RequestBody SubscriptionRequestDTO subscriptionRequestDTO)
            throws EventNotFoundException, SubscriptionNotAllowedException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.subscribeParticipant(eventId, subscriptionRequestDTO.userEmailAddress()));

    }

    @PostMapping("/{eventId}/unsubscribe")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void unSubscribeUser(
            @PathVariable UUID eventId, @Valid @RequestBody SubscriptionRequestDTO subscriptionRequestDTO)
            throws EventNotFoundException, SubscriptionNotFoundException {

        eventService.unSubscribeParticipant(eventId, subscriptionRequestDTO.userEmailAddress());

    }

}
