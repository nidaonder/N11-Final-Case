package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;
import com.nidaonder.User.service.RestaurantRecommenderService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant-recommenders")
@RequiredArgsConstructor
@Validated
public class RestaurantRecommenderController {

    private final RestaurantRecommenderService restaurantRecommenderService;

    @GetMapping("/{userId}")
    public RestResponse<List<RestaurantRecommenderResponse>> getTopThreeRestaurants(
            @PathVariable @Positive Long userId){
        return RestResponse.of(restaurantRecommenderService.getTopThreeNearbyRestaurants(userId));
    }
}