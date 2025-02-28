package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {
    private static final HashMap<Long, Item> itemRepository = new HashMap<>();
    private long nextId = 1;

    public Item getById(Long id) {
        return itemRepository.get(id);
    }

    public List<Item> getAll() {
        return itemRepository.values().stream().toList();
    }

    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(nextId++);
        }
        itemRepository.put(item.getId(), item);
        return item;
    }

    public void deleteById(Long id) {
        itemRepository.remove(id);
    }

    public List<Item> findAllByOwner(Long ownerId) {

        return itemRepository.values().stream()
                .filter(item -> item.getOwner().getId().equals(ownerId))
                .collect(Collectors.toList());
    }

    public List<Item> searchAvailableItems(String text) {
        return itemRepository.values().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable())
                .filter(item -> containsIgnoreCase(item.getName(), text.toLowerCase()) ||
                        containsIgnoreCase(item.getDescription(), text.toLowerCase()))
                .collect(Collectors.toList());
    }

    private boolean containsIgnoreCase(String source, String target) {
        if (source == null || target == null) {
            return false;
        }
        return source.toLowerCase().contains(target);
    }
}