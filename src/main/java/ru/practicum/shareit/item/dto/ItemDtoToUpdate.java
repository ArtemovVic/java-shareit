package ru.practicum.shareit.item.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoToUpdate {
    private String name;
    private String description;
    private Boolean available;
}
