package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import redis.clients.jedis.Jedis;

// Requires a running Docker environment
@Testcontainers
class LogWaitIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379)
      .waitingFor(Wait.forLogMessage(".*Ready to accept connections.*\\n", 1));

    @Test
    void givenLogWait_whenSetAndGet_thenReturnsValue() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getFirstMappedPort())) {
            jedis.set("tool", "Testcontainers");

            assertEquals("Testcontainers", jedis.get("tool"));
        }
    }
}
