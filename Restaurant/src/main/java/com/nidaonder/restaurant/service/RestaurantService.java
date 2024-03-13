package com.nidaonder.restaurant.service;

import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Iterable<RestaurantResponse> findAll();
    Optional<RestaurantResponse> findById(String id);
    RestaurantResponse save(RestaurantSaveRequest request);
    RestaurantResponse update(String id, RestaurantUpdateRequest request);
    void delete(String id);
}
