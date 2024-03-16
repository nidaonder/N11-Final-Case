package com.nidaonder.restaurant.mapper;

import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant requestToEntity(RestaurantSaveRequest request);
    Restaurant responseToEntity(RestaurantResponse response);
    RestaurantSaveRequest entityToRequest(Restaurant restaurant);
    RestaurantResponse entityToResponse(Restaurant restaurant);
    List<RestaurantResponse> entityToListResponse(Iterable<Restaurant> restaurants);
    void update (@MappingTarget Restaurant targetEntity, RestaurantUpdateRequest updatingRequest);
}