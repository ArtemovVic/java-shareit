package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private static final HashMap<Long, User> userRepository = new HashMap<>();
    private long nextId = 1;

    @Override
    public User getById(Long id) {
        if (userRepository.get(id) == null) {
            throw new NotFoundException("User with id = " + id + " not found.");
        }
        return userRepository.get(id);

    }

    @Override
    public List<User> getAll() {
        return userRepository.values().stream().toList();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.remove(id);

    }
}
