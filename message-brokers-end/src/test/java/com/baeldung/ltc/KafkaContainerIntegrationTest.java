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
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

import com.baeldung.ltc.messaging.CampaignEventProducer;
import com.baeldung.ltc.persistence.model.Campaign;

@Testcontainers
class KafkaContainerIntegrationTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer("apache/kafka-native:3.8.0");

    private KafkaConsumer<String, String> createConsumer(String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return new KafkaConsumer<>(props);
    }

    @Test
    void givenSingleMessage_whenProducedAndConsumed_thenValueMatches() {
        Campaign campaign = new Campaign("C1", "Campaign Alpha", "desc");

        CampaignEventProducer producer = new CampaignEventProducer(kafkaContainer.getBootstrapServers(), "single-message-topic");
        producer.publish(campaign);
        producer.close();

        KafkaConsumer<String, String> consumer = createConsumer("test-group-1");
        consumer.subscribe(Collections.singletonList("single-message-topic"));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        consumer.close();

        assertEquals(1, records.count());
        assertEquals("Campaign Alpha", records.iterator().next().value());
    }

    @Test
    void givenMultipleMessages_whenProducedAndConsumed_thenAllValuesMatch() {
        List<Campaign> campaigns = List.of(new Campaign("C1", "Campaign Alpha", "desc"), new Campaign("C2", "Campaign Beta", "desc"), new Campaign("C3", "Campaign Gamma", "desc"));

        CampaignEventProducer producer = new CampaignEventProducer(kafkaContainer.getBootstrapServers(), "batch-message-topic");
        campaigns.forEach(producer::publish);
        producer.close();

        KafkaConsumer<String, String> consumer = createConsumer("test-group-2");
        consumer.subscribe(Collections.singletonList("batch-message-topic"));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        consumer.close();

        List<String> values = new ArrayList<>();
        records.forEach(record -> values.add(record.value()));

        assertEquals(3, values.size());
        assertTrue(values.contains("Campaign Alpha"));
        assertTrue(values.contains("Campaign Beta"));
        assertTrue(values.contains("Campaign Gamma"));
    }
}
