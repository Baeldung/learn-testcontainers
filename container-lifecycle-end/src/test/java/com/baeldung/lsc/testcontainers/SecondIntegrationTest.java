package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;

class SecondIntegrationTest extends RedisContainerBase {

    @Test
    void givenEntry_whenGet_thenReturnsValue() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.set("suite", "second");

            assertEquals("second", jedis.get("suite"));
        }
    }
}
