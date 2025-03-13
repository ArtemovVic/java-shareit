package ru.practicum.shareit.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    @Size(max = 255)
    private String name;
    @Email
    @NotBlank
    private String email;

}
