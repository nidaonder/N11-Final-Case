package com.nidaonder.restaurant.service;

import com.nidaonder.restaurant.core.exception.ErrorMessage;
import com.nidaonder.restaurant.core.exception.ItemExistException;
import com.nidaonder.restaurant.core.exception.ItemNotFoundException;
import com.nidaonder.restaurant.dao.RestaurantRepository;
import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.entity.Restaurant;
import com.nidaonder.restaurant.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Iterable<RestaurantResponse> findAll() {
        return restaurantMapper.entityToListResponse(restaurantRepository.findAll());
    }

    @Override
    public RestaurantResponse findById(String id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Restaurant with ID '{}' not found", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        return restaurantMapper.entityToResponse(restaurant.get());
    }

    @Override
    public RestaurantResponse save(RestaurantSaveRequest request) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(request.id());
        if (restaurant.isPresent()) {
            log.info("Restaurant ID already used: {}", request.id());
            throw new ItemExistException(ErrorMessage.ITEM_ALREADY_EXIST);
        }
        Restaurant newRestaurant = restaurantMapper.requestToEntity(request);
        restaurantRepository.save(newRestaurant);

        newRestaurant.setCreatedAt(LocalDateTime.now());//todo restaurantların base entity fieldları db ye gelmiyor.
        newRestaurant.setUpdatedAt(LocalDateTime.now());
        log.info("Restaurant has been saved: {}", newRestaurant);
        return restaurantMapper.entityToResponse(newRestaurant);
    }

    @Override
    public RestaurantResponse update(String id, RestaurantUpdateRequest request) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Failed to update restaurant with ID '{}': Restaurant does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        Restaurant updatedRestaurant = restaurant.get();
        restaurantMapper.update(updatedRestaurant, request);
        restaurantRepository.save(updatedRestaurant);
        updatedRestaurant.setUpdatedAt(LocalDateTime.now());
        log.info("Restaurant has been saved as updated: {}", updatedRestaurant);
        return restaurantMapper.entityToResponse(updatedRestaurant);
    }

    @Override
    public void delete(String id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Failed to delete restaurant with ID '{}': Restaurant does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        restaurantRepository.deleteById(id);
        log.info("Restaurant has been deleted: {}", restaurant);
    }
}
