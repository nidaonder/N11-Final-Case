package com.nidaonder.UserService.mapper;

import com.nidaonder.UserService.core.BaseMapper;
import com.nidaonder.UserService.dto.request.UserReviewSaveRequest;
import com.nidaonder.UserService.dto.request.UserReviewUpdateRequest;
import com.nidaonder.UserService.dto.response.UserReviewResponse;
import com.nidaonder.UserService.entity.UserReview;
import org.mapstruct.Mapper;

@Mapper
public interface UserReviewMapper extends BaseMapper<UserReview, UserReviewSaveRequest, UserReviewUpdateRequest, UserReviewResponse> {
}
