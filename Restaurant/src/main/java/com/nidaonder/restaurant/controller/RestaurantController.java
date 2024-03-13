package com.nidaonder.restaurant.controller;

import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public Iterable<RestaurantResponse> getAllRestaurants(){
        return restaurantService.findAll();
    }

    @PostMapping
    public RestaurantResponse saveRestaurant(@RequestBody RestaurantSaveRequest request){
        return restaurantService.save(request);
    }
}
