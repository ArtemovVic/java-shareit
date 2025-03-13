package ru.practicum.shareit.request.dto;

import lombok.*;
import ru.practicum.shareit.user.controller.model.UserData;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemRequestDto {
    private long id;
    private String description;
    private UserData requestor;
    private LocalDateTime created;
}
