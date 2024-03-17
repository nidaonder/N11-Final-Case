package com.nidaonder.User.service;

import com.nidaonder.User.client.RestaurantServiceClient;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dao.UserReviewRepository;
import com.nidaonder.User.dto.request.UserReviewSaveRequest;
import com.nidaonder.User.dto.request.UserReviewUpdateRequest;
import com.nidaonder.User.dto.response.UserReviewResponse;
import com.nidaonder.User.entity.User;
import com.nidaonder.User.entity.UserReview;
import com.nidaonder.User.enums.Score;
import com.nidaonder.User.mapper.UserReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserReviewServiceImplTest {

    @InjectMocks
    private UserReviewServiceImpl userReviewService;

    @Mock
    private UserReviewRepository userReviewRepository;

    @Mock
    private UserReviewMapper userReviewMapper;

    @Mock
    private RestaurantServiceClient restaurantServiceClient;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllUserReviews() {
        UserReview userReview1 = new UserReview();
        User user1 = new User();
        userReview1.setScore(Score.FIVE);
        userReview1.setComment("comment1");
        userReview1.setUser(user1);
        userReview1.setRestaurantId("1");

        UserReview userReview2 = new UserReview();
        User user2 = new User();
        userReview2.setScore(Score.FOUR);
        userReview2.setComment("comment2");
        userReview2.setUser(user2);
        userReview2.setRestaurantId("2");

        List<UserReview> userReviews = new ArrayList<>();
        userReviews.add(userReview1);
        userReviews.add(userReview2);

        when(userReviewRepository.findAll()).thenReturn(userReviews);

        UserReviewResponse response1 = new UserReviewResponse(
                userReview1.getId(),
                userReview1.getScore(),
                userReview1.getComment(),
                userReview1.getUser(),
                userReview1.getRestaurantId()
        );
        UserReviewResponse response2 = new UserReviewResponse(
                userReview2.getId(),
                userReview2.getScore(),
                userReview2.getComment(),
                userReview2.getUser(),
                userReview2.getRestaurantId()
        );

        List<UserReviewResponse> expectedResponses = Arrays.asList(response1, response2);

        when(userReviewMapper.entityToListResponse(userReviews)).thenReturn(expectedResponses);

        List<UserReviewResponse> actualResponses = userReviewService.findAll();

        assertEquals(expectedResponses, actualResponses);
        verify(userReviewRepository).findAll();
        verify(userReviewMapper).entityToListResponse(userReviews);
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        Long reviewId = 1L;
        when(userReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userReviewService.findById(reviewId));

        verify(userReviewRepository).findById(reviewId);
    }

    @Test
    void shouldReturnUserReviewResponseWhenReviewExists() {
        Long reviewId = 1L;
        UserReview userReview = new UserReview();
        userReview.setId(reviewId);
        UserReviewResponse expectedResponse = new UserReviewResponse(
                userReview.getId(),
                userReview.getScore(),
                userReview.getComment(),
                userReview.getUser(),
                userReview.getRestaurantId()
        );

        when(userReviewRepository.findById(reviewId)).thenReturn(Optional.of(userReview));
        when(userReviewMapper.entityToResponse(userReview)).thenReturn(expectedResponse);

        UserReviewResponse actualResponse = userReviewService.findById(reviewId);

        assertEquals(expectedResponse, actualResponse);
        verify(userReviewRepository).findById(reviewId);
        verify(userReviewMapper).entityToResponse(userReview);
    }

    @Test
    void shouldSuccessfullySaveUserReview() {
        UserReviewSaveRequest request = new UserReviewSaveRequest(
                Score.FIVE,
                "comment1",
                1L,
                "1"
        );

        UserReview savedUserReview = new UserReview();
        UserReviewResponse expectedResponse = new UserReviewResponse(
                1L,
                request.score(),
                request.comment(),
                new User(),
                request.restaurantId()
        );

        when(userReviewMapper.requestToEntity(request)).thenReturn(savedUserReview);
        when(userReviewMapper.entityToResponse(savedUserReview)).thenReturn(expectedResponse);

        UserReviewResponse actualResponse = userReviewService.save(request);

        assertEquals(expectedResponse, actualResponse);
        verify(userService).checkUserExists(request.userId());
        verify(userReviewRepository).save(savedUserReview);
        verify(userReviewMapper).entityToResponse(savedUserReview);
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExistOnUpdate() {
        Long reviewId = 1L;
        UserReviewUpdateRequest updateRequest = new UserReviewUpdateRequest(
                Score.FIVE,
                "comment1");

        when(userReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userReviewService.update(reviewId, updateRequest));

        verify(userReviewRepository).findById(reviewId);
    }

    @Test
    void shouldUpdateReviewSuccessfully() {
        Long reviewId = 1L;
        UserReview existingReview = new UserReview();
        existingReview.setId(reviewId);
        existingReview.setScore(Score.ONE);
        UserReviewUpdateRequest updateRequest = new UserReviewUpdateRequest(
                Score.FIVE,
                "comment1");

        when(userReviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        UserReviewResponse expectedResponse = new UserReviewResponse(
                reviewId,
                updateRequest.score(),
                updateRequest.comment(),
                existingReview.getUser(),
                existingReview.getRestaurantId()

        );
        when(userReviewMapper.entityToResponse(existingReview)).thenReturn(expectedResponse);

        UserReviewResponse actualResponse = userReviewService.update(reviewId, updateRequest);

        assertEquals(expectedResponse, actualResponse);

        verify(userReviewRepository).findById(reviewId);
        verify(userReviewMapper).update(existingReview, updateRequest);
        verify(userReviewRepository).save(existingReview);
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExistOnDelete() {
        Long reviewId = 1L;

        when(userReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userReviewService.deleteById(reviewId));

        verify(userReviewRepository).findById(reviewId);
    }

    @Test
    void shouldDeleteUserReviewWhenIdExists() {
        Long reviewId = 1L;
        UserReview userReview = new UserReview();
        userReview.setId(reviewId);
        userReview.setRestaurantId("1");
        userReview.setScore(Score.FIVE);

        when(userReviewRepository.findById(reviewId)).thenReturn(Optional.of(userReview));

        userReviewService.deleteById(reviewId);

        verify(userReviewRepository).findById(reviewId);
        verify(userReviewRepository).deleteById(reviewId);
    }
}