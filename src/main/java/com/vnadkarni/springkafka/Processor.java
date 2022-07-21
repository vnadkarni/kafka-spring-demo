package com.vnadkarni.springkafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Processor {

    @Autowired
    public void process (StreamsBuilder builder) {

        final Serde<Integer> integerSerde = Serdes.Integer();
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

//        Create a KStream from StreamsBuilder from hobbit topic
        KStream<Integer, String> textLines = builder.stream("hobbit", Consumed.with(integerSerde, stringSerde));

//        Create a KTable from the stream by splitting the words, grouping by the value (words) and then counting(aggregating)
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(v-> Arrays.asList(v.toLowerCase().split("\\W+")))
                .groupBy((k,v)->v, Grouped.with(stringSerde, stringSerde))
                .count();

//        wordCounts.toStream()
//                .peek((k,v)->System.out.println(k+" observed " + v + "times"))
//                .to("streams-wordcount-output", Produced.with(stringSerde, longSerde));
    }
}
