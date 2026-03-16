package com.baeldung.ltc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

@Testcontainers
class KafkaContainerIntegrationTest {

    @Container
    static KafkaContainer kafkaContainer =
      new KafkaContainer("apache/kafka-native:3.8.0");

    @Test
    void givenSingleMessage_whenProducedAndConsumed_thenValueMatches() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
          kafkaContainer.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
          StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
          StringSerializer.class.getName());

        KafkaProducer<String, String> producer =
          new KafkaProducer<>(producerProps);
        producer.send(
          new ProducerRecord<>("single-message-topic", "key-1",
            "Hello, Kafka!"));
        producer.flush();
        producer.close();

        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
          kafkaContainer.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-1");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
          "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer =
          new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("single-message-topic"));
        ConsumerRecords<String, String> records =
          consumer.poll(Duration.ofSeconds(10));
        consumer.close();

        assertEquals(1, records.count());
        assertEquals("Hello, Kafka!",
          records.iterator().next().value());
    }

    @Test
    void givenMultipleMessages_whenProducedAndConsumed_thenAllValuesMatch() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
          kafkaContainer.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
          StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
          StringSerializer.class.getName());

        KafkaProducer<String, String> producer =
          new KafkaProducer<>(producerProps);
        producer.send(
          new ProducerRecord<>("batch-message-topic", "key-1", "Message 1"));
        producer.send(
          new ProducerRecord<>("batch-message-topic", "key-2", "Message 2"));
        producer.send(
          new ProducerRecord<>("batch-message-topic", "key-3", "Message 3"));
        producer.flush();
        producer.close();

        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
          kafkaContainer.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-2");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
          "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer =
          new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("batch-message-topic"));
        ConsumerRecords<String, String> records =
          consumer.poll(Duration.ofSeconds(10));
        consumer.close();

        List<String> values = new ArrayList<>();
        records.forEach(record -> values.add(record.value()));

        assertEquals(3, values.size());
        assertTrue(values.contains("Message 1"));
        assertTrue(values.contains("Message 2"));
        assertTrue(values.contains("Message 3"));
    }
}
