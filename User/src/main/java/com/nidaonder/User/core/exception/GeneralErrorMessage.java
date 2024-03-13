package com.nidaonder.User.core.exception;

import java.time.LocalDateTime;

public record GeneralErrorMessage(LocalDateTime date, String message, String description) {
}
