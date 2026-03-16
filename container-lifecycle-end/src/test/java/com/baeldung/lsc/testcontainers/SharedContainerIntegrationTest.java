package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import redis.clients.jedis.Jedis;

// Requires a running Docker environment
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SharedContainerIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379);

    @BeforeEach
    void clearRedis() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.flushAll();
        }
    }

    @Test
    @Order(1)
    void givenEntry_whenGet_thenReturnsValue() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.set("language", "Java");

            assertEquals("Java", jedis.get("language"));
        }
    }

    @Test
    @Order(2)
    void givenNoEntry_whenGet_thenReturnsNull() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            assertNull(jedis.get("language"));
        }
    }
}
