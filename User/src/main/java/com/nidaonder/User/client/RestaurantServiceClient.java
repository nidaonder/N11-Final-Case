package com.nidaonder.User.client;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.response.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "restaurant-service", url = "http://localhost:8080/api/v1/restaurants")
public interface RestaurantServiceClient {

    @GetMapping
    ResponseEntity<RestResponse<Iterable<RestaurantResponse>>> getAllRestaurants();
}
