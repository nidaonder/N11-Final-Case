package com.nidaonder.restaurant.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@SolrDocument(solrCoreName = "restaurant")
public class Restaurant {

    @Id
    @Indexed(name = "id", type = "string")
    @NotBlank
    private String id;

    @Indexed(name = "name", type = "string")
    @NotBlank
    private String name;

    @Indexed(name = "description", type = "string")
    private String description;

    @Indexed(name = "average_score", type = "pdouble")
    @NotNull
    private Double averageScore;

    @Indexed(name = "latitude", type = "pdouble")
    @NotNull
    private Double latitude;

    @Indexed(name = "longitude", type = "pdouble")
    @NotNull
    private Double longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
