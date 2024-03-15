package com.nidaonder.restaurant.dto;

import com.nidaonder.restaurant.enums.AverageScoreUpdateType;

public record RestaurantUpdateScoreRequest(Integer score, AverageScoreUpdateType type) {
}
