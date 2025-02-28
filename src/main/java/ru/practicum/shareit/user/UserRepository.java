package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User getById(Long id);

    List<User> getAll();

    User save(User user);

    void deleteById(Long id);
}
