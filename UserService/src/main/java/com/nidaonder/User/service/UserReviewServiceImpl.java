package com.nidaonder.User.service;

import com.nidaonder.User.core.exception.ErrorMessage;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dao.UserReviewRepository;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.entity.User;
import com.nidaonder.User.entity.UserReview;
import com.nidaonder.User.mapper.UserReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReviewServiceImpl implements UserReviewService{

    private final UserReviewRepository userReviewRepository;
    private final UserReviewMapper userReviewMapper;
    private final UserService userService;

    @Override
    public List<UserReviewResponse> findAll() {
        return userReviewMapper.entityToListResponse(userReviewRepository.findAll());
    }

    @Override
    public UserReviewResponse findById(Long id) {
        return userReviewMapper.entityToResponse(userReviewRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND)));
    }

    @Override
    public UserReviewResponse save(UserReviewSaveRequest request) {
        UserReview newUserReview = userReviewMapper.requestToEntity(request);
        userReviewRepository.save(newUserReview);
        log.info("User review has been saved: {}", newUserReview);
        return userReviewMapper.entityToResponse(newUserReview);
    }

    @Override
    public UserReviewResponse update(Long id, UserReviewUpdateRequest request) {
        Optional<UserReview> userReview = userReviewRepository.findById(id);
        if (userReview.isEmpty()) {
            log.info("Failed to update user review with ID '{}': User review does not exist!", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        UserResponse userFromDb = userService.findById(request.user().getId());
        if (userFromDb == null){
            log.info("Failed to update user review with ID '{}': User ID does not exist!", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }

        // TODO restaurant için de aynı kontroller yapılacak!!!!

        UserReview updatedUserReview = userReview.get();
        userReviewMapper.update(updatedUserReview, request);
        userReviewRepository.save(updatedUserReview);
        log.info("User review has been saved as updated: {}", updatedUserReview);
        return userReviewMapper.entityToResponse(updatedUserReview);
    }

    @Override
    public void deleteById(Long id) {

    }
}
