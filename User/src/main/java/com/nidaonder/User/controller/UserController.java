package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdatePasswordRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User Management")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Displays all users.")
    public RestResponse<List<UserResponse>> getAllUser() {
        return RestResponse.of(userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by user ID", description = "Displays requested user.")
    public RestResponse<UserResponse> getUserById(@PathVariable @Positive Long id) {
        return RestResponse.of(userService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Save User", description = "Save User with necessary information.")
    public RestResponse<UserResponse> saveUser(@Valid @RequestBody UserSaveRequest request) {
        return RestResponse.of(userService.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User by ID", description = "Users information can update except password.")
    public RestResponse<UserResponse> updateUser(@PathVariable @Positive Long id,
                                                 @Valid @RequestBody UserUpdateRequest request) {
        return RestResponse.of(userService.update(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update User Password", description = "The update is done by entering the old and new password.")
    public RestResponse<UserResponse> updatePassword(@PathVariable @Positive Long id,
                                                     @Valid @RequestBody UserUpdatePasswordRequest request) {
        return RestResponse.of(userService.updatePassword(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User by ID", description = "The requested user is deleted according to the ID.")
    public RestResponse<Void> deleteUser(@PathVariable @Positive Long id) {
        userService.deleteById(id);
        return RestResponse.empty();
    }
}