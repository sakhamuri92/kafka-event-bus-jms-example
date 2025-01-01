package com.kafka.events.kafka_dynamic_events.domain;

import java.io.Serializable;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {
    private String orderId;
    private String status;
    private double totalAmount;
}