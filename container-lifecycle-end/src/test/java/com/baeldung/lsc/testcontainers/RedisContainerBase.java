package com.baeldung.lsc.testcontainers;

import org.testcontainers.containers.GenericContainer;

// Requires a running Docker environment
abstract class RedisContainerBase {

    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379);

    static {
        redis.start();
    }
}
