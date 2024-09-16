package ru.skillbox.auth_basic_example.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class InMemoryUserProperties {

    private String type;
    private List<User> users;

    @Data
    public static class User {
        private String username;
        private String password;
        private List<String> roles;
    }
}