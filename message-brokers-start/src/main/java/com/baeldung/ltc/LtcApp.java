package com.baeldung.ltc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class LtcApp {

    public static void main(final String... args) {
        SpringApplication.run(LtcApp.class, args);
    }

}
