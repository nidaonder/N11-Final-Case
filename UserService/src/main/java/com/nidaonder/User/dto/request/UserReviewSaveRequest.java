package com.nidaonder.User.dto.request;

import com.nidaonder.User.entity.User;
import com.nidaonder.User.enums.Score;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserReviewSaveRequest(
        @NotNull @Size(max = 5) Score score,
        String comment,
        @NotNull User user,
        @NotNull Long restaurantId
) {
}
