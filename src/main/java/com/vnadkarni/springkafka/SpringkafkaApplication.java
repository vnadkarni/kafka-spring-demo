package com.vnadkarni.springkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafkaStreams
@SpringBootApplication
public class SpringkafkaApplication {

	public static void main(String[] args) {

//		Trasnfers flow of control to Spring Boot.
		SpringApplication.run(SpringkafkaApplication.class, args);
	}

	@Bean
	NewTopic hobbit2() {
		return TopicBuilder.name("hobbit2")
							.partitions(12)
							.replicas(3)
							.build();
	}

	@Bean
	NewTopic counts() {
		return TopicBuilder.name("streams-wordcount-output")
							.partitions(6)
							.replicas(3)
							.build();
	}

}
