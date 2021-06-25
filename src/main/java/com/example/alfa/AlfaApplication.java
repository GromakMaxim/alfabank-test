package com.example.alfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.alfa")
public class AlfaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfaApplication.class, args);
    }

}
