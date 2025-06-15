package com.salah.checkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.salah.checkinservice.client")

public class CheckInServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckInServiceApplication.class, args);
    }

}
