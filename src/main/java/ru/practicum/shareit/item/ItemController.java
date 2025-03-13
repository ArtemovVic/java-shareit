package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.dto.ResponseItemWithComments;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(USER_ID_HEADER) Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("==>Creating item: {}", itemDto);
        ItemDto item = itemService.createItem(userId, itemDto);
        log.info("<==Creating item: {}", item);
        return item;
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(USER_ID_HEADER) Long userId, @PathVariable Long itemId, @RequestBody ItemDtoToUpdate itemDto) {
        log.info("==>Updating item: {}", itemDto);
        ItemDto updatedItem = itemService.updateItem(userId, itemId, itemDto);
        log.info("<==Updating item: {}", itemDto);

        return updatedItem;
    }

    @GetMapping("/{itemId}")
    public ResponseItemWithComments getItemById(@PathVariable Long itemId) {
        log.info("==>Getting item with id: {}", itemId);
        ResponseItemWithComments item = itemService.getItemById(itemId);
        log.info("<==Getting item with id: {}", item.getId());
        return item;
    }

    @GetMapping
    public List<ResponseItemWithComments> getAllItemsByOwner(@RequestHeader(USER_ID_HEADER) Long userId) {
        return itemService.getAllItemsByOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text) {
        return itemService.searchItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId,
            @RequestBody CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
    }
}
