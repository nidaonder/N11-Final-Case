package com.nidaonder.UserService.service;

import com.nidaonder.UserService.core.BaseService;
import com.nidaonder.UserService.core.exception.ErrorMessage;
import com.nidaonder.UserService.core.exception.ItemExistException;
import com.nidaonder.UserService.core.exception.ItemNotFoundException;
import com.nidaonder.UserService.dao.UserRepository;
import com.nidaonder.UserService.dto.request.UserSaveRequest;
import com.nidaonder.UserService.dto.request.UserUpdateRequest;
import com.nidaonder.UserService.dto.response.UserResponse;
import com.nidaonder.UserService.entity.User;
import com.nidaonder.UserService.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements BaseService<User, UserSaveRequest, UserUpdateRequest, UserResponse> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        return userMapper.entityToListResponse(userRepository.findAll());
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.entityToResponse(userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND)));
    }

    @Override
    public UserResponse save(UserSaveRequest request) {
        Optional<User> user = userRepository.findByEmail(request.email());
        if(user.isEmpty()){
            User newUser = userMapper.requestToEntity(request);
            userRepository.save(newUser);
            log.info("User has been saved: {}", newUser);
            return userMapper.entityToResponse(newUser);
        }
        throw new ItemExistException(ErrorMessage.ITEM_EXIST);
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
