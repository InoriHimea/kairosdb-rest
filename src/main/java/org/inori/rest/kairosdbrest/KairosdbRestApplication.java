package org.inori.rest.kairosdbrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KairosdbRestApplication {

    public static void main(String[] args) {
        System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(KairosdbRestApplication.class, args);
    }

}
