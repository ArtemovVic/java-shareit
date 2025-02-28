package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto getItemById(Long itemId);

    List<ItemDto> getAllItemsByOwner(Long userId);

    ItemDto createItem(Long id, ItemDto itemDto);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    List<ItemDto> searchItems(String text);
}
