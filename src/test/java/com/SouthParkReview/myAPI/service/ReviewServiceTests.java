package com.SouthParkReview.myAPI.service;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.ReviewDTO;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.models.Review;
import com.SouthParkReview.myAPI.repository.CitizenRepository;
import com.SouthParkReview.myAPI.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private CitizenRepository citizenRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Citizen citizen;
    private Review review;
    private CitizenDTO citizenDTO;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void init(){
        citizen = Citizen.builder()
                .name("Butters").description("blonde hair").build();
        citizenDTO = CitizenDTO.builder()
                .name("Butters").description("blonde hair").build();
        review = Review.builder()
                .content("norm").title("Mr").stars(4).build();
        reviewDTO = ReviewDTO.builder()
                .content(" review norm").title("Ms").stars(5).build();
    }

    @Test
    public void ReviewService_createReview_returnReviewDTO(){
        when(citizenRepository.findById(citizen.getId())).thenReturn(Optional.of(citizen));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDTO savedReview = reviewService.createReview(citizen.getId(), reviewDTO);

        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewService_findById_ReturnReviewDTO(){
        int reviewId = 1;
        when(reviewRepository.findByCitizenId(reviewId)).thenReturn(List.of(review));

        List<ReviewDTO> savedReview = reviewService.getReviewsByCitizenId(reviewId);

        Assertions.assertThat(savedReview).isNotNull();

    }

    @Test
    public void ReviewService_GetReviewById_ReturnReviewDTO(){
        int reviewId = 1;
        int citizenId = 1;

        review.setCitizen(citizen);
        when(citizenRepository.findById(citizenId)).thenReturn(Optional.of(citizen));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewDTO reviewReturn = reviewService.getReviewById(reviewId,citizenId);

        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void ReviewService_UpdateReview_ReturnReviewDTO(){
        int reviewId = 1;
        int citizenId = 1;
        citizen.setReviews(List.of(review));
        review.setCitizen(citizen);

        when(citizenRepository.findById(citizenId)).thenReturn(Optional.of(citizen));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDTO updatedReview = reviewService.updateReview(citizenId, reviewId, reviewDTO);

        Assertions.assertThat(updatedReview).isNotNull();

    }

    @Test
    public void ReviewService_DeleteReview_ReturnVoid(){
        int reviewId = 1;
        int citizenId = 1;
        citizen.setReviews(List.of(review));
        review.setCitizen(citizen);

        when(citizenRepository.findById(citizenId)).thenReturn(Optional.of(citizen));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(()->reviewService.deleteReview(citizenId, reviewId));
    }
}
