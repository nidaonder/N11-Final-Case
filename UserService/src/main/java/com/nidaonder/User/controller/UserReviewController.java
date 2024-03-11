package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.service.UserReviewService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-reviews")
@RequiredArgsConstructor
@Validated
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping
    public ResponseEntity<RestResponse<List<UserReviewResponse>>> getAllUserReviews() {
        return ResponseEntity.ok(RestResponse.of(userReviewService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserReviewResponse>> getUserReviewById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(RestResponse.of(userReviewService.findById(id)));
    }
}
