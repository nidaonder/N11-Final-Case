package com.nidaonder.UserService.service;

import com.nidaonder.UserService.core.BaseService;
import com.nidaonder.UserService.dao.UserRepository;
import com.nidaonder.UserService.dto.request.UserSaveRequest;
import com.nidaonder.UserService.dto.request.UserUpdateRequest;
import com.nidaonder.UserService.dto.response.UserResponse;
import com.nidaonder.UserService.entity.User;
import com.nidaonder.UserService.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User, UserSaveRequest, UserUpdateRequest, UserResponse> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        return userMapper.entityToListResponse(userRepository.findAll());
    }

    @Override
    public UserResponse findById(Long id) {
        userRepository.findById(id);
        return userMapper.entityToResponse(userRepository.findById(id).get());
    }

    @Override
    public UserResponse save(UserSaveRequest request) {
        return null;
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
