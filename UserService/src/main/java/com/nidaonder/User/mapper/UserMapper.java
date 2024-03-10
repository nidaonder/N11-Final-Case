package com.nidaonder.User.mapper;

import com.nidaonder.User.core.BaseMapper;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User, UserSaveRequest, UserUpdateRequest, UserResponse> {
}
