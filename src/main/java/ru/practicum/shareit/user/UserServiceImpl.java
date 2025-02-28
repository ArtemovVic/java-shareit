package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getById(Long userId) {
        User user = userRepository.getById(userId);
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (isAlreadyRegisteredEmail(userDto.getEmail())) {
            log.error("User with email {} is already registered", userDto.getEmail());
            throw new IllegalArgumentException("User with this email is already registered");
        }
        User savedUser = userRepository.save(UserMapper.toEntity(userDto));
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User updatedUser = userRepository.save(UserMapper.toEntity(userDto));
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean isAlreadyRegisteredEmail(String email) {
        return userRepository.getAll().stream()
                .anyMatch(user -> email.equals(user.getEmail()));
    }

}
