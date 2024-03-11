package com.nidaonder.User.mapper;

import com.nidaonder.User.core.BaseMapper;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.entity.UserReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserReviewMapper extends BaseMapper<UserReview, UserReviewSaveRequest, UserReviewUpdateRequest, UserReviewResponse> {
    @Override
    @Mapping(source = "userId", target = "user.id")
    UserReview requestToEntity(UserReviewSaveRequest request);
}
