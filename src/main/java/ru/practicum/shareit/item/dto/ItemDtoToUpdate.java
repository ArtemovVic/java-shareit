package ru.practicum.shareit.item.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDtoToUpdate {
    //private Long id;
    private String name;
    private String description;
    private Boolean available;
    //private UserDto owner;
    //private ItemRequestDto request;
}
