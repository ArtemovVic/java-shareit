package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoToUpdate;

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
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("==>Getting user with id = {}", userId);
        UserDto user = userService.getById(userId);
        log.info("<==Getting user with id = {}", user.getId());
        return user;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("==>Creating user: {}", userDto);
        UserDto user = userService.createUser(userDto);
        log.info("<==Creating user: {}", user);
        return user;
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDtoToUpdate userDto) {
        UserDto existingUser = userService.getById(userId);

        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            if (!existingUser.getEmail().equals(userDto.getEmail())) {
                if (userService.isAlreadyRegisteredEmail(userDto.getEmail())) {
                    throw new IllegalArgumentException("User with this email is already registered");
                }
            }
            existingUser.setEmail(userDto.getEmail());
        }

        log.info("==>Updating user with id = {}", userId);
        UserDto user = userService.updateUser(userId, existingUser);
        log.info("<==Updating user with id = {}", user.getId());

        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("==>Deleting user with id = {}", userId);
        userService.deleteUser(userId);

    }
}
