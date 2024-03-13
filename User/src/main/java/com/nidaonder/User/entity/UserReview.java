package com.nidaonder.User.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nidaonder.User.core.BaseEntity;
import com.nidaonder.User.enums.Score;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_REVIEW")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_review")
    @SequenceGenerator(name = "user_review", sequenceName = "USER_REVIEW_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "SCORE", length = 10, nullable = false)
    private Score score;

    @Column(name = "COMMENT")
    private String comment;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "ID_USER",
            foreignKey = @ForeignKey(
                    name = "FK_REVIEW_USER"
            ),
            nullable = false
    )
    private User user;

    @NotNull
    @Column(name = "ID_RESTAURANT", nullable = false)
    private Long restaurantId;

    @Override
    public String toString() {
        return "UserReview{" +
                "id=" + id +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                ", restaurantId=" + restaurantId +
                '}';
    }
}