package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

@Component
public class ItemMapper {
    public static ItemDto toDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner() != null ? UserMapper.toDto(item.getOwner()) : null,
                item.getRequest() != null ? ItemRequestMapper.toDto(item.getRequest()) : null
        );
    }

    public static Item toEntity(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                itemDto.getOwner() != null ? UserMapper.toEntity(itemDto.getOwner()) : null,
                itemDto.getRequest() != null ? ItemRequestMapper.toEntity(itemDto.getRequest()) : null
        );
    }
}
