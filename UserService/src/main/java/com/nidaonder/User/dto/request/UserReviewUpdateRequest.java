package com.nidaonder.User.dto.request;

import com.nidaonder.User.entity.User;
import com.nidaonder.User.enums.Score;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserReviewUpdateRequest(
        @NotNull Score score,
        String comment,
        @NotNull User user,
        @NotNull Long restaurantId
) {
}
