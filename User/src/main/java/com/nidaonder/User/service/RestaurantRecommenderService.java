package com.nidaonder.User.service;

import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;

import java.util.List;

public interface RestaurantRecommenderService {
    List<RestaurantRecommenderResponse> getTopThreeNearbyRestaurants(Long id);
}
