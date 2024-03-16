package com.nidaonder.User.service;

import com.nidaonder.User.client.RestaurantService;
import com.nidaonder.User.core.exception.ErrorMessage;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;
import com.nidaonder.User.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantRecommenderServiceImpl implements RestaurantRecommenderService{

    private final UserService userService;
    private final RestaurantService restaurantService;

    @Override
    public List<RestaurantRecommenderResponse> getTopThreeNearbyRestaurants(Long id)  {
        UserResponse user = userService.findById(id);
        if (user == null) {
            log.info("User with ID '{}' not found", id);
            throw new ItemNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }

        List<RestaurantRecommenderResponse> nearbyRestaurants = restaurantService
                .getNearbyRestaurants(user.latitude(), user.longitude());

        if(nearbyRestaurants.isEmpty()){
            log.info("No restaurants were found within 10 km for user : {}", user);
            throw new ItemNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND);
        }

        log.info("Restaurants with in 10 km : {}", nearbyRestaurants );

        List<RestaurantRecommenderResponse> topThreeRestaurant = nearbyRestaurants.stream()
                .sorted((restaurant1, restaurant2) -> Double.compare(
                        calculateRecommendationScore(restaurant2.restaurant().averageScore(), restaurant2.distance()),
                        calculateRecommendationScore(restaurant1.restaurant().averageScore(), restaurant1.distance())))
                .limit(3)
                .toList();
        log.info("Restaurants that should recommend : {}", topThreeRestaurant );

        return topThreeRestaurant;
    }

    private Double calculateRecommendationScore(Double averageScore, Double distance){
        Double totalScoreFromScore = (averageScore * 7) / 5;
        Double totalScoreFromDistance = (3 - (distance * 0.3));

        return totalScoreFromScore + totalScoreFromDistance;
    }
}