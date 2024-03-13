package com.nidaonder.User.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserSaveRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 100) String surname,
        @NotBlank @Size(max = 100) @Email String email,
        @NotBlank @Size(min = 8, max = 30) String password,
        @Past LocalDate birthDate,
        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") @NotNull Double latitude,
        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") @NotNull Double longitude
) {
}
