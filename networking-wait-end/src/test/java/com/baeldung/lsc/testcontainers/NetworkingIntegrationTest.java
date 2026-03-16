package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import redis.clients.jedis.Jedis;

// Requires a running Docker environment
@Testcontainers
class NetworkingIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379);

    @Test
    void givenRedisContainer_whenGetMappedPort_thenConnectsSuccessfully() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.set("key", "value");

            assertEquals("value", jedis.get("key"));
        }
    }

    @Test
    void givenRedisContainer_whenGetFirstMappedPort_thenConnectsSuccessfully() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getFirstMappedPort())) {
            jedis.set("key", "value");

            assertEquals("value", jedis.get("key"));
        }
    }
}
