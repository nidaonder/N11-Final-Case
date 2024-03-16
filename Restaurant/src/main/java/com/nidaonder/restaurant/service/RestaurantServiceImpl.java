package com.nidaonder.restaurant.service;

import com.nidaonder.restaurant.core.exception.ErrorMessage;
import com.nidaonder.restaurant.core.exception.ItemExistException;
import com.nidaonder.restaurant.core.exception.ItemNotFoundException;
import com.nidaonder.restaurant.dao.RestaurantRepository;
import com.nidaonder.restaurant.dto.RestaurantResponse;
import com.nidaonder.restaurant.dto.RestaurantSaveRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateRequest;
import com.nidaonder.restaurant.dto.RestaurantUpdateScoreRequest;
import com.nidaonder.restaurant.entity.Restaurant;
import com.nidaonder.restaurant.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public List<RestaurantResponse> findAll() {
        return restaurantMapper.entityToListResponse(restaurantRepository.findAll());
    }

    @Override
    public RestaurantResponse findById(String id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Restaurant with ID '{}' not found", id);
            throw new ItemNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND);
        }
        return restaurantMapper.entityToResponse(restaurant.get());
    }

    @Override
    public RestaurantResponse save(RestaurantSaveRequest request) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(request.id());

        if (restaurant.isPresent()) {
            log.info("Restaurant ID already used: {}", request.id());
            throw new ItemExistException(ErrorMessage.RESTAURANT_ALREADY_EXIST);
        }

        Restaurant newRestaurant = restaurantMapper.requestToEntity(request);
        newRestaurant.setAverageScore(0.0);
        newRestaurant.setCommentCount(0);
        newRestaurant.setCreatedAt(LocalDateTime.now());
        newRestaurant.setUpdatedAt(LocalDateTime.now());
        restaurantRepository.save(newRestaurant);
        log.info("Restaurant has been saved: {}", newRestaurant);
        return restaurantMapper.entityToResponse(newRestaurant);
    }

    @Override
    public RestaurantResponse update(String id, RestaurantUpdateRequest request) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Failed to update restaurant with ID '{}': Restaurant does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND);
        }
        Restaurant updatedRestaurant = restaurant.get();
        restaurantMapper.update(updatedRestaurant, request);
        updatedRestaurant.setUpdatedAt(LocalDateTime.now());
        restaurantRepository.save(updatedRestaurant);
        log.info("Restaurant has been saved as updated: {}", updatedRestaurant);
        return restaurantMapper.entityToResponse(updatedRestaurant);
    }

    @Override
    public RestaurantResponse updateAverageScore(String id, RestaurantUpdateScoreRequest request) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Failed to update average score, restaurant with ID '{}': Restaurant does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND);
        }

        Restaurant updatedRestaurant = restaurant.get();
        Double averageScore = updatedRestaurant.getAverageScore();
        Integer commentCount = updatedRestaurant.getCommentCount();
        Double newAverageScore = null;
        switch (request.type()){
            case ADD -> {
                newAverageScore = ((request.score() + (averageScore * commentCount)) / (commentCount + 1)) ;
                commentCount++;
            }
            case UPDATE -> {
                newAverageScore = ((averageScore * commentCount) + request.score()) / commentCount;
            }
            case DELETE -> {
                commentCount--;
                newAverageScore = (commentCount > 0)
                        ? ((averageScore * (commentCount + 1)) - request.score()) / commentCount
                        : 0.0;
            }
        }

        updatedRestaurant.setAverageScore(newAverageScore);
        updatedRestaurant.setCommentCount(commentCount);
        updatedRestaurant.setUpdatedAt(LocalDateTime.now());

        restaurantRepository.save(updatedRestaurant);
        log.info("Restaurant's average score has been updated: {}", updatedRestaurant);
        return restaurantMapper.entityToResponse(updatedRestaurant);
    }

    @Override
    public void delete(String id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            log.info("Failed to delete restaurant with ID '{}': Restaurant does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND);
        }
        restaurantRepository.deleteById(id);
        log.info("Restaurant has been deleted: {}", restaurant);
    }
}