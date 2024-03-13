package com.nidaonder.User.dto.response;

import com.nidaonder.User.enums.Status;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        Status status,
        LocalDate birthDate,
        Double latitude,
        Double longitude
) {
}
