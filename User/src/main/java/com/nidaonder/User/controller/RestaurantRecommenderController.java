package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;
import com.nidaonder.User.service.RestaurantRecommenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Restaurant Recommender Controller", description = "Restaurant Recommendation")
public class RestaurantRecommenderController {

    private final RestaurantRecommenderService restaurantRecommenderService;

    @GetMapping("/{userId}")
    @Operation(summary = "Restaurant Recommendation based on User ID",
            description = "The best 3 restaurant suggestions are given according to the distance to the location entered by the user and the restaurant score.")
    public RestResponse<List<RestaurantRecommenderResponse>> getTopThreeRestaurants(
            @PathVariable @Positive Long userId){
        return RestResponse.of(restaurantRecommenderService.getTopThreeNearbyRestaurants(userId));
    }
}