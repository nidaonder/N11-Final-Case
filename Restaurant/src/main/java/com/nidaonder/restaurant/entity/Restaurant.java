package com.nidaonder.restaurant.entity;

import com.nidaonder.restaurant.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SolrDocument(solrCoreName = "restaurant")
public class Restaurant extends BaseEntity {

    @Id
    @Indexed(name = "id", type = "string")
    @NotBlank
    private String id;

    @Indexed(name = "name", type = "string")
    @NotBlank
    private String name;

    @Indexed(name = "description", type = "string")
    @NotBlank
    private String description;

    @Indexed(name = "average_score", type = "pdouble")
    @NotNull
    private Double averageScore;

    @Indexed(name = "latitude", type = "pdouble")
    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;

    @Indexed(name = "longitude", type = "pdouble")
    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @Indexed(name = "comment_count", type = "int")
    @NotNull
    private Integer commentCount;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", averageScore=" + averageScore +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", commentCount=" + commentCount +
                '}';
    }
}