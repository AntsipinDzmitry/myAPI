package com.SouthParkReview.myAPI.service;

import com.SouthParkReview.myAPI.DTO.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(int citizenId, ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewsByCitizenId(int id);

    ReviewDTO getReviewById(int citizenId, int reviewId);

    ReviewDTO updateReview(int citizenId, int reviewId, ReviewDTO reviewDTO);

    void deleteReview(int citizenId, int reviewId);
}
