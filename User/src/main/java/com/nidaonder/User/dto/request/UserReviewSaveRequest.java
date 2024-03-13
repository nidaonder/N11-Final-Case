package com.nidaonder.User.dto.request;

import com.nidaonder.User.enums.Score;
import jakarta.validation.constraints.NotNull;

public record UserReviewSaveRequest(
        @NotNull Score score,
        String comment,
        @NotNull Long userId,
        @NotNull Long restaurantId
) {
}
