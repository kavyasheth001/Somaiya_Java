package com.classconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassConnectApplication.class, args);
        System.out.println("🚀 ClassConnect Backend is running on http://localhost:8080");
        System.out.println("📚 API Documentation: http://localhost:8080/api");
    }
}

