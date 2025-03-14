package ru.practicum.shareit.item.dto;

import lombok.Data;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

@Data
public class ResponseItemWithComments {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Collection<CommentDto> comments;
    private BookingDto lastBooking;
    private BookingDto nextBooking;

}
