package com.kafka.events.kafka_dynamic_events.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.events.kafka_dynamic_events.domain.Order;


@Service
public class EventConsumer {
    // http://localhost:8080/api/events/order_delivered
    // {
    //     "orderId": "12345",
    //     "status": "order_delivered",
    //     "totalAmount": 150.00
    //   }
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    // Inject JmsTemplate and ObjectMapper
    public EventConsumer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }
//  private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
//         @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
//         public void consume(OrderEvent event) {
//             LOGGER.info(String.format("Order Event consumed in stock service ==> %s", event.toString()));
//             //save order event to database
//         }

    // KafkaListener consumes the payload from Kafka
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        System.out.println("STARTED LISTENING FOR TOPIC MESSAGES");
        try {
            // Deserialize the message (assuming the payload is a JSON Order object)
            Order order = objectMapper.readValue(message, Order.class);
            String eventName = order.getStatus();  // Assuming status is the event name

            // Dynamically determine the JMS queue name
            String jmsQueueName = eventName + "Queue";  // e.g., ORDER_DELIVEREDQueue

            // Send the message to the respective JMS queue
            sendToJmsQueue(jmsQueueName, order);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process message from Kafka: " + e.getMessage());
        }
    }

    // Forward the message to the respective JMS queue
    private void sendToJmsQueue(String queueName, Order order) {
        // Send the order object to the corresponding JMS queue
        jmsTemplate.convertAndSend(queueName, order);
        System.out.println("Message sent to JMS queue: " + queueName);
    }
}