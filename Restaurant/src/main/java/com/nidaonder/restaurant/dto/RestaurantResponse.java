package com.nidaonder.restaurant.dto;

public record RestaurantResponse(String id,
                                 String name,
                                 String description,
                                 Double averageScore,
                                 Double latitude,
                                 Double longitude,
                                 Integer commentCount) {
}
