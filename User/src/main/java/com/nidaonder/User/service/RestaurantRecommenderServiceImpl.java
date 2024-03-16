package com.nidaonder.User.service;

import com.nidaonder.User.client.RestaurantServiceClient;
import com.nidaonder.User.core.exception.ErrorMessage;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;
import com.nidaonder.User.dto.response.RestaurantResponse;
import com.nidaonder.User.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantRecommenderServiceImpl implements RestaurantRecommenderService{

    private final UserService userService;
    private final RestaurantServiceClient restaurantServiceClient;

    @Override
    public List<RestaurantRecommenderResponse> getTopThreeNearbyRestaurants(Long id) {
        UserResponse user = userService.findById(id);
        if (user == null) {
            log.info("User with ID '{}' not found", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }

        Double userLat = user.latitude();
        Double userLon = user.longitude();

        //TODO: Nullable hatasını handle etmek gerekebilir.

        List<RestaurantResponse> allRestaurants = Objects
                .requireNonNull(restaurantServiceClient.getAllRestaurants().getBody())
                .getData();
        log.info("{} restaurant found", allRestaurants.size() );

        List<RestaurantRecommenderResponse> nearbyRestaurants = allRestaurants.stream()
                .map(restaurant -> new RestaurantRecommenderResponse(
                        restaurant,
                        calculateDistance(userLat, userLon, restaurant.latitude(), restaurant.longitude())))
                .filter(restaurantInfo -> restaurantInfo.distance() <= 10.0)
                .toList();

        if(nearbyRestaurants.isEmpty()){
            log.info("No restaurants were found within 10 km for user : {}", user);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
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

    private Double calculateDistance(Double userLat, Double userLon, Double restaurantLat, Double restaurantLon){
        final int EARTH_RADIUS = 6371;

        double userLatRad = Math.toRadians(userLat);
        double userLonRad = Math.toRadians(userLon);
        double restaurantLatRad = Math.toRadians(restaurantLat);
        double restaurantLonRad = Math.toRadians(restaurantLon);

        double x = (restaurantLonRad - userLonRad) * Math.cos((userLatRad + restaurantLatRad) / 2);
        double y = (restaurantLatRad - userLatRad);

        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }

    private Double calculateRecommendationScore(Double averageScore, Double distance){
        Double totalScoreFromScore = (averageScore * 7) / 5;
        Double totalScoreFromDistance = (3 - (distance * 0.3));

        return totalScoreFromScore + totalScoreFromDistance;
    }
}