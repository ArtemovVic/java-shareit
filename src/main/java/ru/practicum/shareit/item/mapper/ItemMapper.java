package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ResponseItemWithComments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.user.controller.mappers.UserMapper;

import java.util.Collection;

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

    public static ResponseItemWithComments toDtoWithComments(Item item,
                                                             Collection<CommentDto> comments
    ) {
        ResponseItemWithComments response = new ResponseItemWithComments();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setAvailable(item.getAvailable());
        response.setComments(comments);
        return response;
    }

    public static ResponseItemWithComments toDtoWithCommentsAndDate(Item item,
                                                                    Collection<CommentDto> comments,
                                                                    BookingDto lastBooking,
                                                                    BookingDto nextBooking
    ) {
        ResponseItemWithComments response = new ResponseItemWithComments();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setAvailable(item.getAvailable());
        response.setComments(comments);
        response.setLastBooking(lastBooking);
        response.setNextBooking(nextBooking);
        return response;
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
