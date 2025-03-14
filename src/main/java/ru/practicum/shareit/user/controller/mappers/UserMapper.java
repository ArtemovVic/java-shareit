package ru.practicum.shareit.user.controller.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.controller.model.UserData;
import ru.practicum.shareit.user.controller.model.CreateUserRequest;
import ru.practicum.shareit.user.dao.model.User;

@Component
public class UserMapper {
    public static UserData toDto(User user) {
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setName(user.getName());
        userData.setEmail(user.getEmail());
        return userData;
    }

    public static User toEntity(UserData userData) {
        return new User(
                userData.getId(),
                userData.getName(),
                userData.getEmail()
        );
    }

    public static User toEntity(CreateUserRequest userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
