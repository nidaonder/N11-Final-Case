package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdatePasswordRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.service.UserService;
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
public class UserController {

    private final UserService userService;

    @GetMapping
    public RestResponse<List<UserResponse>> getAllUser() {
        return RestResponse.of(userService.findAll());
    }

    @GetMapping("/{id}")
    public RestResponse<UserResponse> getUserById(@PathVariable @Positive Long id) {
        return RestResponse.of(userService.findById(id));
    }

    @PostMapping
    public RestResponse<UserResponse> saveUser(@Valid @RequestBody UserSaveRequest request) {
        return RestResponse.of(userService.save(request));
    }

    @PutMapping("/{id}")
    public RestResponse<UserResponse> updateUser(@PathVariable @Positive Long id,
                                                                 @Valid @RequestBody UserUpdateRequest request) {
        return RestResponse.of(userService.update(id, request));
    }

    @PatchMapping("/{id}")
    public RestResponse<UserResponse> updatePassword(@PathVariable @Positive Long id,
                                                                     @Valid @RequestBody UserUpdatePasswordRequest request) {
        return RestResponse.of(userService.updatePassword(id, request));
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteUser(@PathVariable @Positive Long id) {
        userService.deleteById(id);
        return RestResponse.empty();
    }
}