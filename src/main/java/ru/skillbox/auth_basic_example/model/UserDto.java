package ru.skillbox.auth_basic_example.model;


import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String username;

    private String password;
}
