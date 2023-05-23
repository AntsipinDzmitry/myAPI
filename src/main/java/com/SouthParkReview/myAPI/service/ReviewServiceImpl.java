package com.SouthParkReview.myAPI.service;

import com.SouthParkReview.myAPI.DTO.ReviewDTO;
import com.SouthParkReview.myAPI.exceptions.CitizenNotFoundException;
import com.SouthParkReview.myAPI.exceptions.ReviewNotFoundException;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.models.Review;
import com.SouthParkReview.myAPI.repository.CitizenRepository;
import com.SouthParkReview.myAPI.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;

    private CitizenRepository citizenRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, CitizenRepository citizenRepository){
        this.reviewRepository = reviewRepository;
        this.citizenRepository = citizenRepository;
    }

    @Override
    public ReviewDTO createReview(int citizenId, ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(()->
                new CitizenNotFoundException("Citizen with associated review not found"));
        review.setCitizen(citizen);
        Review newReview = reviewRepository.save(review);
        return mapToDTO(newReview);
    }

    @Override
    public List<ReviewDTO> getReviewsByCitizenId(int id) {
        List<Review> reviews = reviewRepository.findByCitizenId(id);
        return reviews.stream().map(review->mapToDTO(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(int reviewId, int citizenId) {
        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(()->
                new CitizenNotFoundException("Citizen with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->
                new ReviewNotFoundException("Review with associated citizen not found"));
        if(review.getCitizen().getId() != citizen.getId()){
            throw new ReviewNotFoundException("This review doesn't belong to a citizen");
        }
        return mapToDTO(review);
    }

    @Override
    public ReviewDTO updateReview(int citizenId, int reviewId, ReviewDTO reviewDTO) {
        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(()->
                new CitizenNotFoundException("Citizen with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->
                new ReviewNotFoundException("Review with associated citizen not found"));
        if(review.getCitizen().getId() != citizen.getId()){
            throw new ReviewNotFoundException("This review doesn't belong to a citizen");}

            review.setTitle(reviewDTO.getTitle());
            review.setContent(reviewDTO.getContent());
            review.setStars(reviewDTO.getStars());
            Review updatedReview = reviewRepository.save(review);

            return mapToDTO(updatedReview);

    }

    @Override
    public void deleteReview(int citizenId, int reviewId) {
        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(()->
                new CitizenNotFoundException("Citizen with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->
                new ReviewNotFoundException("Review with associated citizen not found"));
        if(review.getCitizen().getId() != citizen.getId()){
            throw new ReviewNotFoundException("This review doesn't belong to a citizen");}
        reviewRepository.delete(review);
    }

    private ReviewDTO mapToDTO(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setTitle(review.getTitle());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setStars(review.getStars());
        return reviewDTO;
    }
    private Review mapToEntity(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());
        review.setStars(reviewDTO.getStars());
        return review;
    }

}
