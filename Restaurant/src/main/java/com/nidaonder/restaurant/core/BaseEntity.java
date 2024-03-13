package com.nidaonder.restaurant.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.solr.core.mapping.Indexed;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseEntity {

    @Indexed(name = "created_at", type = "pdate")
    private LocalDateTime createdAt;

    @Indexed(name = "updated_at", type = "pdate")
    private LocalDateTime updatedAt;
}
