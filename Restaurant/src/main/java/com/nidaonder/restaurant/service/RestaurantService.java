package com.nidaonder.restaurant.service;

import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateScoreRequest;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Iterable<RestaurantResponse> findAll();
    RestaurantResponse findById(String id);
    RestaurantResponse save(RestaurantSaveRequest request);
    RestaurantResponse update(String id, RestaurantUpdateRequest request);
    RestaurantResponse addReviewAndUpdateAverageScore(String id, RestaurantUpdateScoreRequest request);
    void delete(String id);
}
