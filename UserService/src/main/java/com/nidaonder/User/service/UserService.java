package com.nidaonder.User.service;

import com.nidaonder.User.core.BaseService;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.entity.User;

public interface UserService extends BaseService<User, UserSaveRequest, UserUpdateRequest, UserResponse> {
}
