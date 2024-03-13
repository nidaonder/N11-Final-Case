package com.nidaonder.restaurant.dto;

public record RestaurantSaveRequest(String id,
                                    String name,
                                    String description,
                                    Double latitude,
                                    Double longitude) {
}
