package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dao.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemRequestRepository {
    private final Map<Long, ItemRequest> requests = new HashMap<>();
    private long nextId = 1;

    public ItemRequest getById(Long id) {
        return requests.get(id);
    }

    public Map<Long, ItemRequest> getAll() {
        return new HashMap<>(requests);
    }

    public ItemRequest save(ItemRequest request) {
        if (request.getId() == null) {
            request.setId(nextId++);
        }
        requests.put(request.getId(), request);
        return request;
    }

    public void deleteById(Long id) {
        requests.remove(id);
    }

    public List<ItemRequest> findAllByRequestor(User requestor) {
        return requests.values().stream()
                .filter(itemRequest -> itemRequest.getRequestor().equals(requestor))
                .collect(Collectors.toList());
    }
}
