package ru.skillbox.auth_basic_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class AuthBasicExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthBasicExampleApplication.class, args);
    }

}
