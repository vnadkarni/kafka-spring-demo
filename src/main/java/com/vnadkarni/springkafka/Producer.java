package com.vnadkarni.springkafka;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;


@Component      // Instantiated and injected so KafkaTemplate<> gets properties
@RequiredArgsConstructor        //Args Constructor made so KafkaTemplate
public class Producer {
    private final KafkaTemplate<Integer, String> template;
    Faker faker;
    private Logger logger = LoggerFactory.getLogger(Producer.class);
//      logger.warn("test warning");
//      logger.error(e.toString());

    @EventListener(ApplicationStartedEvent.class)
    public void generate() {
        faker = Faker.instance();

//        Returns a Flux of <String> containing HGTG quotes
        final Flux<String> quotes = Flux.fromStream(Stream.generate(()->faker.hitchhikersGuideToTheGalaxy().quote()));

//        delays the flux by 1 sec, and sends to Kafka topic
        quotes.delayElements(Duration.ofSeconds(1))
                .map(q->template.send("hobbit", faker.random().nextInt(42), q))
                .subscribe();
    }
}
