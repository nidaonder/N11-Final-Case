package com.nidaonder.UserService.mapper;

import com.nidaonder.UserService.core.BaseMapper;
import com.nidaonder.UserService.dto.request.UserSaveRequest;
import com.nidaonder.UserService.dto.request.UserUpdateRequest;
import com.nidaonder.UserService.dto.response.UserResponse;
import com.nidaonder.UserService.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User, UserSaveRequest, UserUpdateRequest, UserResponse> {
}
