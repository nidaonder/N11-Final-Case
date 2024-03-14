package com.nidaonder.User.dto.response;

public record RestaurantResponse(String id,
                                 String name,
                                 String description,
                                 Double averageScore,
                                 Double latitude,
                                 Double longitude) {
}