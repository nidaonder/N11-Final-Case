package com.nidaonder.restaurant.controller;

import com.nidaonder.restaurant.core.RestResponse;
import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateScoreRequest;
import com.nidaonder.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public RestResponse<Iterable<RestaurantResponse>> getAllRestaurants(){
        return RestResponse.of(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    public RestResponse<RestaurantResponse> getRestaurantById(@PathVariable String id) {
        return RestResponse.of(restaurantService.findById(id));
    }

    @PostMapping
    public RestResponse<RestaurantResponse> saveRestaurant(@RequestBody @Valid RestaurantSaveRequest request){
        return RestResponse.of(restaurantService.save(request));
    }

    @PutMapping("/{id}")
    public RestResponse<RestaurantResponse> updateRestaurant(@PathVariable String id,
                                                                             @RequestBody @Valid RestaurantUpdateRequest request) {
        return RestResponse.of(restaurantService.update(id, request));
    }

    @PutMapping("/{id}/average-score")
    public RestResponse<RestaurantResponse> updateAverageScore(@PathVariable String id,
                                                                               @RequestBody RestaurantUpdateScoreRequest request) {
        return RestResponse.of(restaurantService.updateAverageScore(id, request));
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.delete(id);
        return RestResponse.empty();
    }
}