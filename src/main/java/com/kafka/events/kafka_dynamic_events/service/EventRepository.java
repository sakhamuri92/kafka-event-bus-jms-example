package com.kafka.events.kafka_dynamic_events.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafka.events.kafka_dynamic_events.domain.Event;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventName(String eventName);
}
