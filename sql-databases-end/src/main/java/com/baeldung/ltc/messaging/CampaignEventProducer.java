package com.baeldung.ltc.messaging;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.baeldung.ltc.persistence.model.Campaign;

public class CampaignEventProducer implements AutoCloseable {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public CampaignEventProducer(String bootstrapServers, String topic) {
        this.topic = topic;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.producer = new KafkaProducer<>(props);
    }

    public void publish(Campaign campaign) {
        producer.send(new ProducerRecord<>(topic, campaign.getCode(), campaign.getName()));
        producer.flush();
    }

    @Override
    public void close() {
        producer.close();
    }
}
