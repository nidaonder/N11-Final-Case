package com.nidaonder.restaurant.service;

import com.nidaonder.restaurant.dao.RestaurantRepository;
import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.entity.Restaurant;
import com.nidaonder.restaurant.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Iterable<RestaurantResponse> findAll() {
        return restaurantMapper.entityToListResponse(restaurantRepository.findAll());
    }

    @Override
    public Optional<RestaurantResponse> findById(String id) {
        return Optional.empty();
    }

    @Override //tODO NOT FINISHED
    public RestaurantResponse save(RestaurantSaveRequest request) {
        Restaurant save = restaurantRepository.save(restaurantMapper.requestToEntity(request));
        return restaurantMapper.entityToResponse(save);
    }

    @Override
    public RestaurantResponse update(String id, RestaurantUpdateRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
