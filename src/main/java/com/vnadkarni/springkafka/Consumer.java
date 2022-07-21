package com.vnadkarni.springkafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = {"hobbit"}, groupId = "spring-boot-consumer")
    public void consume (String quote) {
        System.out.println("received = " + quote);
    }
}
