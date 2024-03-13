package com.nidaonder.restaurant.mapper;

import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.entity.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant requestToEntity(RestaurantSaveRequest request);
    Restaurant responseToEntity(RestaurantResponse response);
    RestaurantSaveRequest entityToRequest(Restaurant restaurant);
    RestaurantResponse entityToResponse(Restaurant restaurant);
    Iterable<RestaurantResponse> entityToListResponse(Iterable<Restaurant> restaurants);
}
