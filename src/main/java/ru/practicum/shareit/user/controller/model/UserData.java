package ru.practicum.shareit.user.controller.model;

import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserData {
    private Long id;
    @Size(max = 255)
    private String name;
    private String email;
}
