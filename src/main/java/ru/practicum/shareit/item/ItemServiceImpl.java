package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepositoryImpl;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepositoryImpl userRepository;

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.getById(itemId);
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAllItemsByOwner(Long userId) {
        return itemRepository.findAllByOwner(userId).stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto createItem(Long id, ItemDto itemDto) {
        User owner = userRepository.getById(id);
        if (owner == null) {
            throw new NotFoundException("User with id= " + id + " not found.");
        }

        Item item = ItemMapper.toEntity(itemDto);
        item.setOwner(owner);
        Item savedItem = itemRepository.save(item);
        return ItemMapper.toDto(savedItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item existingItem = itemRepository.getById(itemId);
        if (existingItem.getOwner() == null || !existingItem.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Only the owner can edit the item.");
        }

        Item updatedItem = itemRepository.save(ItemMapper.toEntity(itemDto));
        return ItemMapper.toDto(updatedItem);
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return itemRepository.searchAvailableItems(text).stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }


}
