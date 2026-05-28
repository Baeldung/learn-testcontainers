package com.baeldung.ltc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;

class FirstIntegrationTest extends RedisContainerBase {

    @Test
    void givenEntry_whenGet_thenReturnsValue() {
        try (Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            jedis.set("suite", "first");

            assertEquals("first", jedis.get("suite"));
        }
    }
}
