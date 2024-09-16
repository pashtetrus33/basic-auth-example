package ru.skillbox.auth_basic_example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.auth_basic_example.entity.Role;
import ru.skillbox.auth_basic_example.entity.RoleType;
import ru.skillbox.auth_basic_example.entity.User;
import ru.skillbox.auth_basic_example.model.UserDto;
import ru.skillbox.auth_basic_example.service.UserService;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> getPublic() {
        return ResponseEntity.ok("Called public method!");
    }

    @PostMapping("/account")
    public ResponseEntity<UserDto> createUserAccount(@RequestBody UserDto userDto, @RequestParam RoleType roleType) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createAccount(userDto, roleType));
    }

    private UserDto createAccount(UserDto userDto, RoleType roleType) {
        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        User newAccount = userService.createNewAccount(user, Role.from(roleType));


        return UserDto.builder()
                .username(newAccount.getUsername())
                .password(newAccount.getPassword())
                .build();
    }
}
