package com.nidaonder.restaurant.dto;

public record RestaurantUpdateRequest(String name,
                                      String description,
                                      Double latitude,
                                      Double longitude) {
}
