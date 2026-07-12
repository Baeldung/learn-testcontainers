package com.baeldung.ltc;

import org.springframework.boot.SpringApplication;

public class TestLtcApp {

    public static void main(String[] args) {
        SpringApplication.from(LtcApp::main)
          .with(TestcontainersConfiguration.class)
          .run(args);
    }
}
