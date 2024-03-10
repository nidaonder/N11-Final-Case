package com.nidaonder.UserService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nidaonder.UserService.core.BaseEntity;
import com.nidaonder.UserService.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
    @SequenceGenerator(name = "user", sequenceName = "USER_ID_SEQ")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @NotBlank
    @Column(name = "SURNAME", length = 100, nullable = false)
    private String surname;

    @NotBlank
    @Email
    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 8, max = 30)
    @Column(name = "PASSWORD", length = 30, nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 10, nullable = false)
    private Status status;

    @Past
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "user"
    )
    @JsonIgnore
    private List<UserReview> userReviews;

}