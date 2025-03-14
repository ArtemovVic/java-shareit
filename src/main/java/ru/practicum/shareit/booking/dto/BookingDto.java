package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.controller.model.UserData;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private long id;
    @NonNull
    @Future
    private LocalDateTime start;
    @NonNull
    @Future
    private LocalDateTime end;
    private Long itemId;
    @NonNull
    private ItemDto item;
    @NonNull
    private UserData booker;
    private String status;
}
