package com.nidaonder.User.dto.response;

import com.nidaonder.User.entity.User;
import com.nidaonder.User.enums.Score;

public record UserReviewResponse (
        Long id,
        Score score,
        String comment,
        User user,
        Long restaurantId
){
}
