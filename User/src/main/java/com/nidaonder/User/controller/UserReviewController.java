package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.service.UserReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-reviews")
@RequiredArgsConstructor
@Validated
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping
    public RestResponse<List<UserReviewResponse>> getAllUserReviews() {
        return RestResponse.of(userReviewService.findAll());
    }

    @GetMapping("/{id}")
    public RestResponse<UserReviewResponse> getUserReviewById(@PathVariable @Positive Long id) {
        return RestResponse.of(userReviewService.findById(id));
    }

    @PostMapping
    public RestResponse<UserReviewResponse> saveUserReview(@Valid @RequestBody UserReviewSaveRequest request) {
        return RestResponse.of(userReviewService.save(request));
    }

    @PutMapping("/{id}")
    public RestResponse<UserReviewResponse> updateUserReview(@PathVariable @Positive Long id,
                                                                             @Valid @RequestBody UserReviewUpdateRequest request) {
        return RestResponse.of(userReviewService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteUserReview(@PathVariable @Positive Long id) {
        userReviewService.deleteById(id);
        return RestResponse.empty();
    }
}
