package com.nidaonder.UserService.dao;

import com.nidaonder.UserService.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
}
