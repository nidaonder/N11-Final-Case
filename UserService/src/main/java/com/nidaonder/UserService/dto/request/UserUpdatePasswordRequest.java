package com.nidaonder.UserService.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordRequest(
        @NotBlank @Size(min = 8, max = 30) String oldPassword,
        @NotBlank @Size(min = 8, max = 30) String newPassword,
        @NotBlank @Size(min = 8, max = 30) String newPasswordVerify
) {
}
