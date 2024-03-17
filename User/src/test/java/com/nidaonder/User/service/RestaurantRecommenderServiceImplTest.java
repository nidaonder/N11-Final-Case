package com.nidaonder.User.service;

import com.nidaonder.User.client.RestaurantService;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dto.response.RestaurantRecommenderResponse;
import com.nidaonder.User.dto.response.RestaurantResponse;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestaurantRecommenderServiceImplTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantRecommenderServiceImpl restaurantRecommenderService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowsExceptionWhenUserNotFound() {
        Long userId = 1L;
        UserResponse response = null;

        when(userService.findById(userId)).thenReturn(response);

        assertThrows(ItemNotFoundException.class, () -> restaurantRecommenderService.getTopThreeNearbyRestaurants(userId));
    }

    @Test
    void shouldThrowsExceptionWhenRestaurantNotFound() {
        Long userId = 1L;
        UserResponse response1 = new UserResponse(
                1L,
                "test1",
                "surname1",
                "testuser1@gmail.com",
                Status.ACTIVE,
                LocalDate.of(1996, 6, 6),
                10.01,
                15.01);

        when(userService.findById(userId)).thenReturn(response1);
        when(restaurantService.getNearbyRestaurants(response1.latitude(), response1.longitude())).thenReturn(Collections.emptyList());

        assertThrows(ItemNotFoundException.class, () -> restaurantRecommenderService.getTopThreeNearbyRestaurants(userId));
    }

    @Test
    void shouldReturnsTopThreeRestaurants() {
        Long userId = 1L;
        UserResponse response1 = new UserResponse(
                1L,
                "test1",
                "surname1",
                "testuser1@gmail.com",
                Status.ACTIVE,
                LocalDate.of(1996, 6, 6),
                10.01,
                15.01);

        when(userService.findById(userId)).thenReturn(response1);
        RestaurantRecommenderResponse restaurant1 = new RestaurantRecommenderResponse(
                new RestaurantResponse(
                        "1","Restaurant1", "", 5.0, 10.0, 11.0), 3.0);
        RestaurantRecommenderResponse restaurant2 = new RestaurantRecommenderResponse(
                new RestaurantResponse(
                        "2","Restaurant2", "", 5.0, 10.0, 11.0), 3.0);
        RestaurantRecommenderResponse restaurant3 = new RestaurantRecommenderResponse(
                new RestaurantResponse(
                        "3","Restaurant3", "", 5.0, 10.0, 11.0), 3.0);

        List<RestaurantRecommenderResponse> recommendList = new ArrayList<>();
        recommendList.add(restaurant1);
        recommendList.add(restaurant2);
        recommendList.add(restaurant3);

        when(restaurantService.getNearbyRestaurants(response1.latitude(), response1.longitude())).thenReturn(recommendList);

        List<RestaurantRecommenderResponse> actualResult = restaurantRecommenderService.getTopThreeNearbyRestaurants(userId);

        assertEquals(recommendList.size(), actualResult.size());
    }

}