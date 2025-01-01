package com.kafka.events.kafka_dynamic_events.controller;

import java.util.List;
import java.util.concurrent.Flow.Subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.events.kafka_dynamic_events.domain.Order;
import com.kafka.events.kafka_dynamic_events.domain.QueueSubscription;
import com.kafka.events.kafka_dynamic_events.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/register")
    public ResponseEntity<String> registerEvent(@RequestParam String eventName,
                                                @RequestParam List<String> queueNames) {
        eventService.registerEvent(eventName, queueNames);
        return ResponseEntity.ok("Event registered successfully!");
    }

    @PostMapping("/{eventName}")
    public ResponseEntity<String> publishEvent(@PathVariable String eventName,
                                               @RequestBody Order payload) throws JsonProcessingException {
        eventService.publishEvent(eventName, payload);
        return ResponseEntity.ok("Event published successfully!");
    }

    @GetMapping("/{eventName}/subscribers")
    public ResponseEntity<List<QueueSubscription>> getSubscribers(@PathVariable String eventName) {
        List<QueueSubscription> subscribers = eventService.getSubscribersByEventName(eventName);
        if (subscribers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscribers);
    }
}