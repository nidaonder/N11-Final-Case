package com.nidaonder.User.client;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.core.exception.BusinessException;
import com.nidaonder.User.core.exception.ErrorMessage;
import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;
import com.nidaonder.User.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantServiceClient restaurantServiceClient;

    public List<RestaurantResponse> getAllRestaurants (){
        try{
            RestResponse<List<RestaurantResponse>> body = restaurantServiceClient.getAllRestaurants().getBody();
            if (body != null) {
                List<RestaurantResponse> data = body.getData();
                return data != null ? data : Collections.emptyList();
            } else {
                log.error("Response body is null");
                return Collections.emptyList();
            }
        }catch (Exception e){
            throw new BusinessException(ErrorMessage.SOMETHING_WENT_WRONG);
        }
    }

    public List<RestaurantRecommenderResponse> getNearbyRestaurants(Double latitude, Double longitude) {
        List<RestaurantResponse> allRestaurants = getAllRestaurants();
        log.info("{} restaurant found", allRestaurants.size());
        return allRestaurants.stream()
                .map(restaurant -> new RestaurantRecommenderResponse(
                        restaurant,
                        calculateDistance(latitude, longitude, restaurant.latitude(), restaurant.longitude())))
                .filter(restaurantInfo -> restaurantInfo.distance() <= 10.0)
                .toList();
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
}