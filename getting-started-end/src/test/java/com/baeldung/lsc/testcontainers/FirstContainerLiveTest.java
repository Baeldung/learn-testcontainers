package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import redis.clients.jedis.Jedis;

class FirstContainerLiveTest {

    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379);

    @BeforeAll
    static void startContainer() {
        redis.start();
    }

    @AfterAll
    static void stopContainer() {
        redis.stop();
    }

    @Test
    void givenRedisContainer_whenSetAndGet_thenValueIsRetrieved() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.set("course", "Testcontainers");
            String value = jedis.get("course");

            assertEquals("Testcontainers", value);
        }
    }
}
