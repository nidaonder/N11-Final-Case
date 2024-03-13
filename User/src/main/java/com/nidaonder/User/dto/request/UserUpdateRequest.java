package com.nidaonder.User.dto.request;

import com.nidaonder.User.enums.Status;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserUpdateRequest(
        @NotNull @Size(max = 100) String name,
        @NotNull @Size(max = 100) String surname,
        @NotNull @Size(max = 100) @Email String email,
        @NotNull @Size(max = 10) Status status,
        @Past LocalDate birthDate,
        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") @NotNull Double latitude,
        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") @NotNull Double longitude
) {
}
