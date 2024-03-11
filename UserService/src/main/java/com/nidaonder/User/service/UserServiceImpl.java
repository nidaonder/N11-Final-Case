package com.nidaonder.User.service;

import com.nidaonder.User.core.exception.BusinessException;
import com.nidaonder.User.core.exception.ErrorMessage;
import com.nidaonder.User.core.exception.ItemExistException;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dao.UserRepository;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdatePasswordRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.entity.User;
import com.nidaonder.User.enums.Status;
import com.nidaonder.User.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

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
        if(user.isPresent()){
            log.info("User e-mail address already used: {}", request.email());
            throw new ItemExistException(ErrorMessage.ITEM_ALREADY_EXIST);
        }
        User newUser = userMapper.requestToEntity(request);
        newUser.setStatus(Status.ACTIVE);
        userRepository.save(newUser);
        log.info("User has been saved: {}", newUser);
        return userMapper.entityToResponse(newUser);
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            log.info("Failed to update user with ID '{}': User does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }

        Optional<User> userByEmail = userRepository.findByEmail(request.email());
        if(userByEmail.isPresent() && !userByEmail.get().getId().equals(id)){
            log.info("Failed to update user with ID '{}': E-mail '{}' is already used by another user.", id, request.email());
            throw new ItemExistException(ErrorMessage.ITEM_ALREADY_EXIST);
        }

        User updatedUser = user.get();
        userMapper.update(updatedUser, request);
        userRepository.save(updatedUser);
        log.info("User has been saved as updated: {}", updatedUser);
        return userMapper.entityToResponse(updatedUser);
    }

    @Override
    public UserResponse updatePassword(Long id, UserUpdatePasswordRequest request) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            log.info("Failed to update user with ID '{}': User does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        if (!user.get().getPassword().equals(request.oldPassword())){
            log.info("Update password failed for user with ID '{}': The current password is incorrect.", id);
            throw new BusinessException(ErrorMessage.WRONG_PASSWORD);
        }
        if (user.get().getPassword().equals(request.newPassword())){
            log.info("Update password failed for user with ID '{}': The new password must be different from the current password.", id);
            throw new BusinessException(ErrorMessage.NEW_PASSWORD_CANNOT_BE_SAME);
        }
        if (!request.newPassword().equals(request.newPasswordVerify())){
            log.info("Update password failed for user with ID '{}': The new passwords do not match.", id);
            throw new BusinessException(ErrorMessage.NEW_PASSWORDS_DO_NOT_MATCH);
        }
        User updatedUser = user.get();
        updatedUser.setPassword(request.newPassword());
        userRepository.save(updatedUser);
        log.info("Password updated successfully for user with ID '{}'.", id);
        return userMapper.entityToResponse(updatedUser);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            log.info("Failed to delete user with ID '{}': User does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        userRepository.deleteById(id);
        log.info("User has been deleted: {}", user);
    }
}
