package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.dto.ResponseItemWithComments;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dao.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ResponseItemWithComments getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id: " + itemId + " not found.")
                );

        List<CommentDto> comments = getCommentsByItemId(itemId);
        return ItemMapper.toDtoWithComments(item, comments);
    }

    @Override
    public List<ResponseItemWithComments> getAllItemsByOwner(Long userId) {
        List<ResponseItemWithComments> result = new ArrayList<>();

        List<Item> items = itemRepository.findAllByOwnerId(userId);

        LocalDateTime now = LocalDateTime.now();

        for (Item i: items) {

            BookingDto lastBooking = bookingRepository.findLastBooking(i.getId(), now)
                    .stream()
                    .findFirst()
                    .map(BookingMapper::toDto)
                    .orElse(null);

            BookingDto nextBooking =  bookingRepository.findNextBooking(i.getId(), now).stream()
                    .findFirst()
                    .map(BookingMapper::toDto)
                    .orElse(null);

            List<CommentDto> comments = getCommentsByItemId(i.getId());

            ResponseItemWithComments itemWithComments = ItemMapper.toDtoWithCommentsAndDate(i, comments, lastBooking, nextBooking);


            result.add(itemWithComments);

        }

        return result;
    }

    @Override
    public ItemDto createItem(Long id, ItemDto itemDto) {
        User owner = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found.")
                );

        Item item = ItemMapper.toEntity(itemDto);
        item.setOwner(owner);
        Item savedItem = itemRepository.save(item);
        return ItemMapper.toDto(savedItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDtoToUpdate itemDto) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id: " + itemId + " not found.")
                );
        ;
        if (existingItem.getOwner() == null || !existingItem.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Only the owner can edit the item.");
        }
        if (itemDto.getName() != null) {
            existingItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            existingItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            existingItem.setAvailable(itemDto.getAvailable());
        }

        Item updatedItem = itemRepository.save(existingItem);
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

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id:" + userId + " not found"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id:" + itemId + " not found"));
        boolean hasBooked = bookingRepository.existsByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now());
        if (!hasBooked) {
            throw new RuntimeException("The user has not rented the item or the rental period has not yet expired.");
        }
        Comment comment = CommentMapper.toEntity(commentDto);
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByItemId(Long itemId) {
        List<Comment> comments = commentRepository.findAllByItemId(itemId);
        return comments.stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }


}
