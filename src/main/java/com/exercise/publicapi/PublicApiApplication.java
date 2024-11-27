package com.exercise.publicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = "com.exercise")
@ComponentScan(basePackages = {"com.exercise.userservice", "com.exercise.listingservice", "com.exercise.publicapi"})
public class PublicApiApplication {
    public static void main(String[] args) { SpringApplication.run(PublicApiApplication.class, args); }
}
