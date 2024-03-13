package com.nidaonder.User.dto.request;

import com.nidaonder.User.enums.Score;
import jakarta.validation.constraints.NotNull;

public record UserReviewUpdateRequest(
        @NotNull Score score,
        String comment
) {
}
