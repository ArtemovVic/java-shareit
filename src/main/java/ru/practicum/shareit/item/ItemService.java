package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.dto.ResponseItemWithComments;

import java.util.List;

public interface ItemService {
    ResponseItemWithComments getItemById(Long itemId);

    List<ResponseItemWithComments> getAllItemsByOwner(Long userId);

    ItemDto createItem(Long id, ItemDto itemDto);

    ItemDto updateItem(Long userId, Long itemId, ItemDtoToUpdate itemDto);

    List<ItemDto> searchItems(String text);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);

    List<CommentDto> getCommentsByItemId(Long itemId);
}
