package com.kafka.events.kafka_dynamic_events.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.events.kafka_dynamic_events.domain.Event;
import com.kafka.events.kafka_dynamic_events.domain.Order;
import com.kafka.events.kafka_dynamic_events.domain.QueueSubscription;

import jakarta.transaction.Transactional;
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void registerEvent(String eventName, List<String> queueNames) {
        Event event = Event.builder()
                .eventName(eventName)
                .subscribers(new ArrayList<>())
                .build();

        for (String queue : queueNames) {
            QueueSubscription subscription = QueueSubscription.builder()
                    .queueName(queue)
                    .event(event)
                
                    .build();
                    event.getSubscribers().add(subscription);
        }
        eventRepository.save(event);
    }

    public void publishEvent(String eventName, Order payload) throws JsonProcessingException {
        Event event = eventRepository.findByEventName(eventName)
        .orElseThrow(() -> new RuntimeException("Event not found"));
        // org.springframework.messaging.Message<Order> message = MessageBuilder.withPayload(payload)
        //             .setHeader(KafkaHeaders.TOPIC, eventName).build();
        String orderJson = objectMapper.writeValueAsString(payload);
        kafkaTemplate.send("order_delivered", orderJson);


        // for (QueueSubscription subscriber : event.getSubscribers()) {
        //     jmsTemplate.convertAndSend(subscriber.getQueueName(), payload);
        // }
    }
      public List<QueueSubscription> getSubscribersByEventName(String eventName) {
        Event event = eventRepository.findByEventName(eventName)                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getSubscribers();
    }
}

