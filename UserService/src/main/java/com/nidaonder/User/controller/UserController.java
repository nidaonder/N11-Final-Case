package com.nidaonder.User.controller;

import com.nidaonder.User.core.RestResponse;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RestResponse<List<UserResponse>>> getAllUser() {
        return ResponseEntity.ok(RestResponse.of(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserResponse>> getUserById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(RestResponse.of(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserResponse>> saveUser(@Valid @RequestBody UserSaveRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.save(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<UserResponse>> updateUser(@PathVariable @Positive Long id,
                                                                 @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> deleteUser(@PathVariable @Positive Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(RestResponse.empty());
    }
}