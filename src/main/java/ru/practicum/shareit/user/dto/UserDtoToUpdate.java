package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDtoToUpdate {
    private String name;
    @Email
    private String email;
}
