package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.controller.model.UpdateUserRequest;
import ru.practicum.shareit.user.controller.model.UserData;
import ru.practicum.shareit.user.controller.model.CreateUserRequest;

import java.util.List;

public interface UserService {
    UserData getById(Long userId);

    List<UserData> getAllUsers();

    UserData createUser(CreateUserRequest userDto);

    UserData updateUser(Long userId, UpdateUserRequest userData);

    void deleteUser(Long userId);

    boolean isAlreadyRegisteredEmail(String email);
}
