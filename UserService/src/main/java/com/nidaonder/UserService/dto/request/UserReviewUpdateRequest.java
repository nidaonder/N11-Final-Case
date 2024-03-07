package com.nidaonder.UserService.dto.request;

import com.nidaonder.UserService.entity.User;
import com.nidaonder.UserService.enums.Score;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * gözden çeçir! update işlemleri için neler olmamalı?
 */
public record UserReviewUpdateRequest(
        @NotNull @Size(max = 5) Score score,
        String comment,
        @NotNull User user,
        @NotNull Long restaurantId
) {
}
