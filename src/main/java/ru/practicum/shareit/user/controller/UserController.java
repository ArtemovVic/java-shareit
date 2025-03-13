package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.controller.model.UserData;
import ru.practicum.shareit.user.controller.model.CreateUserRequest;
import ru.practicum.shareit.user.controller.model.UpdateUserRequest;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public UserData getUserById(@PathVariable Long userId) {
        log.info("==>Getting user with id = {}", userId);
        UserData user = userService.getById(userId);
        log.info("<==Getting user with id = {}", user.getId());
        return user;
    }

    @GetMapping
    public List<UserData> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserData createUser(@Valid @RequestBody CreateUserRequest userDto) {
        log.info("==>Creating user: {}", userDto);
        UserData user = userService.createUser(userDto);
        log.info("<==Creating user: {}", user);
        return user;
    }

    @PatchMapping("/{userId}")
    public UserData updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequest userDto) {
        log.info("==>Updating user with id = {}", userId);
        UserData user = userService.updateUser(userId, userDto);
        log.info("<==Updating user with id = {}", user.getId());
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("==>Deleting user with id = {}", userId);
        userService.deleteUser(userId);

    }
}
