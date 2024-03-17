package com.nidaonder.restaurant.controller;

import com.nidaonder.restaurant.core.RestResponse;
import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateScoreRequest;
import com.nidaonder.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Validated
@Tag(name = "Restaurant Controller", description = "Restaurant Management")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Get all Restaurants", description = "Displays all restaurants.")
    public RestResponse<Iterable<RestaurantResponse>> getAllRestaurants(){
        return RestResponse.of(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Restaurant by restaurant ID", description = "Displays requested restaurant.")
    public RestResponse<RestaurantResponse> getRestaurantById(@PathVariable String id) {
        return RestResponse.of(restaurantService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Save Restaurant", description = "Save Restaurant with necessary information.")
    public RestResponse<RestaurantResponse> saveRestaurant(@RequestBody @Valid RestaurantSaveRequest request){
        return RestResponse.of(restaurantService.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Restaurant by ID",
            description = "Restaurant informations updated according to the entered ID.")
    public RestResponse<RestaurantResponse> updateRestaurant(@PathVariable String id,
                                                             @RequestBody @Valid RestaurantUpdateRequest request) {
        return RestResponse.of(restaurantService.update(id, request));
    }

    @PutMapping("/{id}/average-score")
    @Operation(summary = "Update Restaurant Average Score by ID",
            description = "Requests sent from the User Service are used to update the Restaurant Average Score.")
    public RestResponse<RestaurantResponse> updateAverageScore(@PathVariable String id,
                                                               @RequestBody RestaurantUpdateScoreRequest request) {
        return RestResponse.of(restaurantService.updateAverageScore(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Restaurant by ID", description = "The requested Restaurant is deleted according to the ID.")
    public RestResponse<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.delete(id);
        return RestResponse.empty();
    }
}