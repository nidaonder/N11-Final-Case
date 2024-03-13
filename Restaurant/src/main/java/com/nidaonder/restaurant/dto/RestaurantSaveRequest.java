package com.nidaonder.restaurant.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public record RestaurantSaveRequest(@NotBlank String id,
                                    @NotBlank String name,
                                    @NotBlank String description,
                                    @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") Double latitude,
                                    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") Double longitude) {
}
