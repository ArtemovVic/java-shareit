package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(Long userId);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);

    boolean isAlreadyRegisteredEmail(String email);
}
