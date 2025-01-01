
package com.kafka.events.kafka_dynamic_events.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.events.kafka_dynamic_events.domain.Order;


@Component
public class StockServiceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceListener.class);
    @Autowired
    private  ObjectMapper objectMapper;

    @JmsListener(destination = "order_deliveredQueue")
    public void processStockQueue(String orderEvent) {
        LOGGER.info(String.format("Stock Service Sending Notification ==> %s", orderEvent));
    }

}