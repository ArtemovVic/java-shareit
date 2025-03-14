package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.controller.model.UpdateUserRequest;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.controller.model.UserData;
import ru.practicum.shareit.user.controller.model.CreateUserRequest;
import ru.practicum.shareit.user.controller.mappers.UserMapper;
import ru.practicum.shareit.user.dao.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserData getById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found.")
                );
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserData> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserData createUser(CreateUserRequest userDto) {
        User user = UserMapper.toEntity(userDto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserData updateUser(Long userId, UpdateUserRequest userDto) {
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found.")
                );

        String name = userDto.getName();
        if (name != null && !name.isBlank()) {
            updatedUser.setName(name);
        }

        if (userDto.getEmail() != null) {
            if (!updatedUser.getEmail().equals(userDto.getEmail())) {
                if (isAlreadyRegisteredEmail(userDto.getEmail())) {
                    throw new IllegalArgumentException("User with this email is already registered");
                }
            }
            updatedUser.setEmail(userDto.getEmail());
        }

        return UserMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean isAlreadyRegisteredEmail(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> email.equals(user.getEmail()));
    }

}
