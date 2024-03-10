package com.nidaonder.UserService.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserSaveRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 100) String surname,
        @NotBlank @Size(max = 100) @Email String email,
        @NotBlank @Size(min = 8, max = 30) String password,

        //@NotNull @Size(max = 10) Status status,@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        @Past LocalDate birthDate,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
}
