package com.nidaonder.User.service;

import com.nidaonder.User.core.BaseService;
import com.nidaonder.User.dao.UserReviewRepository;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.entity.UserReview;
import com.nidaonder.User.mapper.UserReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewServiceImpl implements UserReviewService{

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
