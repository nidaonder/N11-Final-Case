package com.nidaonder.UserService.dto.response;

import com.nidaonder.UserService.entity.User;
import com.nidaonder.UserService.enums.Score;

public record UserReviewResponse (
        Long id,
        Score score,
        String comment,
        User user,
        Long restaurantId
){
}
