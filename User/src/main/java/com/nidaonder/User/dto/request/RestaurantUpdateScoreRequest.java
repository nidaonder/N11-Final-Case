package com.nidaonder.User.dto.request;

import com.nidaonder.User.enums.AverageScoreUpdateType;

public record RestaurantUpdateScoreRequest(Integer score,
                                           AverageScoreUpdateType type) {
}
