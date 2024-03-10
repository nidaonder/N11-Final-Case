package com.nidaonder.User.service;

import com.nidaonder.User.core.BaseService;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.entity.UserReview;

public interface UserReviewService extends BaseService<UserReview, UserReviewSaveRequest, UserReviewUpdateRequest, UserReviewResponse> {
}
