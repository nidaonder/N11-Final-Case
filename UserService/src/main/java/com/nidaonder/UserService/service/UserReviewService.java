package com.nidaonder.UserService.service;

import com.nidaonder.UserService.core.BaseService;
import com.nidaonder.UserService.dao.UserReviewRepository;
import com.nidaonder.UserService.dto.request.UserReviewSaveRequest;
import com.nidaonder.UserService.dto.request.UserReviewUpdateRequest;
import com.nidaonder.UserService.dto.response.UserReviewResponse;
import com.nidaonder.UserService.entity.UserReview;
import com.nidaonder.UserService.mapper.UserReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewService implements BaseService<UserReview, UserReviewSaveRequest, UserReviewUpdateRequest, UserReviewResponse> {

    private final UserReviewRepository userReviewRepository;
    private final UserReviewMapper userReviewMapper;

    @Override
    public List<UserReviewResponse> findAll() {
        return null;
    }

    @Override
    public UserReviewResponse findById(Long id) {
        return null;
    }

    @Override
    public UserReviewResponse save(UserReviewSaveRequest request) {
        return null;
    }

    @Override
    public UserReviewResponse update(Long id, UserReviewUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
