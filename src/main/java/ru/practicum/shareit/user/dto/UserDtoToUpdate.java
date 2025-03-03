package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoToUpdate {
    private String name;
    @Email
    private String email;
}
