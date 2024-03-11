package com.nidaonder.User.dto.request;

import com.nidaonder.User.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserUpdateRequest(
        @NotNull @Size(max = 100) String name,
        @NotNull @Size(max = 100) String surname,
        @NotNull @Size(max = 100) @Email String email,
        @NotNull @Size(max = 10) Status status,
        @Past LocalDate birthDate,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
}
