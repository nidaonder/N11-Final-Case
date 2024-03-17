package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.service.UserReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Review Controller", description = "User Review Management")
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping
    @Operation(summary = "Get all User Reviews", description = "Displays all User Reviews.")
    public RestResponse<List<UserReviewResponse>> getAllUserReviews() {
        return RestResponse.of(userReviewService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Review by ID", description = "Displays requested User Review.")
    public RestResponse<UserReviewResponse> getUserReviewById(@PathVariable @Positive Long id) {
        return RestResponse.of(userReviewService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Save User Review", description = "Save User Review with necessary information.")
    public RestResponse<UserReviewResponse> saveUserReview(@Valid @RequestBody UserReviewSaveRequest request) {
        return RestResponse.of(userReviewService.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User Review by ID", description = "Score and comment are updated according to the entered ID.")
    public RestResponse<UserReviewResponse> updateUserReview(@PathVariable @Positive Long id,
                                                             @Valid @RequestBody UserReviewUpdateRequest request) {
        return RestResponse.of(userReviewService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User Review by ID", description = "The requested User Review is deleted according to the ID.")
    public RestResponse<Void> deleteUserReview(@PathVariable @Positive Long id) {
        userReviewService.deleteById(id);
        return RestResponse.empty();
    }
}
