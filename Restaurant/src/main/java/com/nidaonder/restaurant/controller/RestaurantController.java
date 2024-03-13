package com.nidaonder.restaurant.controller;

import com.nidaonder.restaurant.core.RestResponse;
import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RestResponse<Iterable<RestaurantResponse>>> getAllRestaurants(){
        return ResponseEntity.ok(RestResponse.of(restaurantService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RestaurantResponse>> getRestaurantById(@PathVariable String id) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<RestResponse<RestaurantResponse>> saveRestaurant(@RequestBody @Valid RestaurantSaveRequest request){
        return ResponseEntity.ok(RestResponse.of(restaurantService.save(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<RestaurantResponse>> updateRestaurant(@PathVariable String id,
                                                                             @RequestBody @Valid RestaurantUpdateRequest request) {
        return ResponseEntity.ok(RestResponse.of(restaurantService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> deleteRestaurant(@PathVariable String id) {
        restaurantService.delete(id);
        return ResponseEntity.ok(RestResponse.empty());
    }
}
