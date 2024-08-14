package com.example.internOrderService.service;

import com.example.sharedLibrary.model.OrderEvent;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    public void sendMsg(OrderEvent orderEvent){
        Message<OrderEvent> msg = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, "order")
                .build();
        kafkaTemplate.send(msg);
    }
}
