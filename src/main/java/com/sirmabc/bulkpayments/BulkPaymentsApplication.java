package com.sirmabc.bulkpayments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableAsync
@EnableScheduling
public class BulkPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulkPaymentsApplication.class, args);
    }

}
