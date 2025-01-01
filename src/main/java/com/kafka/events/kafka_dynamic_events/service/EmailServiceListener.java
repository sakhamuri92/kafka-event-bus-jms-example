package com.kafka.events.kafka_dynamic_events.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class EmailServiceListener {

    // @JmsListener(destination = "email-queue")
    // public void processEmailQueue(EventPayload payload) {
    //     System.out.println("Email Service Processing: " + payload);
    // }
}
