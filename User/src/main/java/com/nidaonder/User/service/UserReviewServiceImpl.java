package com.nidaonder.User.service;

import com.nidaonder.User.client.RestaurantServiceClient;
import com.nidaonder.User.core.exception.ErrorMessage;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dao.UserReviewRepository;
import com.nidaonder.User.dto.request.RestaurantUpdateScoreRequest;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
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
    private final RestaurantServiceClient restaurantServiceClient;

    @Override
    public List<UserReviewResponse> findAll() {
        return userReviewMapper.entityToListResponse(userReviewRepository.findAll());
    }

    @Override
    public UserReviewResponse findById(Long id) {
        Optional<UserReview> userReview = userReviewRepository.findById(id);
        if (userReview.isEmpty()) {
            log.info("User review with ID '{}' not found", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        return userReviewMapper.entityToResponse(userReview.get());
    }

    @Override
    public UserReviewResponse save(UserReviewSaveRequest request) {
        userService.findById(request.userId());//TODO Burda dönen değeri handle etmeli miyim boşsa throw atmalı mıyım zaten service bunu kontrol ediyor.
        UserReview newUserReview = userReviewMapper.requestToEntity(request);
        //restorana git average score'ı güncelle
        restaurantServiceClient.addReviewAndUpdateAverageScore(request.restaurantId(), new RestaurantUpdateScoreRequest(request.score().getValue()));
        log.info("Average score has been updated for related restaurant");
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
        UserReview updatedUserReview = userReview.get();
        userReviewMapper.update(updatedUserReview, request);
        userReviewRepository.save(updatedUserReview);
        log.info("User review has been saved as updated: {}", updatedUserReview);
        return userReviewMapper.entityToResponse(updatedUserReview);
    }

    @Override
    public void deleteById(Long id) {
        Optional<UserReview> userReview = userReviewRepository.findById(id);
        if (userReview.isEmpty()) {
            log.info("Failed to delete user review with ID '{}': User review does not exist.", id);
            throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
        }
        userReviewRepository.deleteById(id);
        log.info("User review has been deleted: {}", userReview);
    }
}
