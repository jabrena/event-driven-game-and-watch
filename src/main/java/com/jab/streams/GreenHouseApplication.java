package com.jab.streams;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class GreenHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenHouseApplication.class, args);
    }

    @Bean
    public Function<String, String> transform() {
        return String::toUpperCase;
    }


    static class TestSource {

        private AtomicInteger counter = new AtomicInteger(0);

        @Bean
        public Supplier<String> sendTestData() {
            return () -> {
                String value = "message" + counter.incrementAndGet();
                sleep(1);
                LOGGER.info("Creating: " + value);
                return value;
            };
        }

    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) { }
    }

    static class TestSink {

        @Bean
        public Consumer<String> receive() {

            return payload -> {
                sleep(2);
                LOGGER.info("Data received: " + payload);
                throw new RuntimeException("Katakroker");
            };
        }

        @Bean
        public Consumer<String> receive2() {

            return payload -> {
                LOGGER.info("Data received from DLX: " + payload);
            };
        }

    }
}
