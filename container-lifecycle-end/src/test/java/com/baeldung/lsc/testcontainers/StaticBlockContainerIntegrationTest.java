package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import redis.clients.jedis.Jedis;

// Requires a running Docker environment
class StaticBlockContainerIntegrationTest {

    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379);

    static {
        redis.start();
    }

    @Test
    void givenEntry_whenGet_thenReturnsValue() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.set("tool", "Testcontainers");

            assertEquals("Testcontainers", jedis.get("tool"));
        }
    }
}
