package com.kafka.events.kafka_dynamic_events.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafka.events.kafka_dynamic_events.domain.Event;
import com.kafka.events.kafka_dynamic_events.domain.QueueSubscription;

import java.util.List;

public interface QueueSubscriptionRepository extends JpaRepository<QueueSubscription, Long> {
    List<QueueSubscription> findByEvent(Event event);
}

