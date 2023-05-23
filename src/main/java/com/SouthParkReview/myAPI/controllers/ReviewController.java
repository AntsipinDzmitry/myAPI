package com.SouthParkReview.myAPI.controllers;

import com.SouthParkReview.myAPI.DTO.ReviewDTO;
import com.SouthParkReview.myAPI.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/citizen/{citizenId}/review")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable(value = "citizenId") int citizenId,
                                                  @RequestBody ReviewDTO reviewDTO){
        return new ResponseEntity<>(reviewService.createReview(citizenId, reviewDTO), HttpStatus.CREATED);
    }
    @GetMapping("/citizen/{citizenId}/reviews")
    public List<ReviewDTO> getReviewsByCitizenId(@PathVariable(value = "citizenId") int citizenId){
        return reviewService.getReviewsByCitizenId(citizenId);
    }
    @GetMapping("/citizen/{citizenId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable(value = "citizenId") int citizenId
            , @PathVariable(value = "reviewId") int reviewId){
        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId, citizenId);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }
    @PutMapping("/citizen/{citizenId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable(value = "citizenId") int citizenId,
                                                        @PathVariable(value = "reviewId") int reviewId,
                                                      @RequestBody ReviewDTO reviewDTO){
        ReviewDTO updatedReviewDTO = reviewService.updateReview(citizenId, reviewId, reviewDTO);
        return new ResponseEntity<>(updatedReviewDTO, HttpStatus.OK);
    }
    @DeleteMapping("/citizen/{citizenId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "citizenId") int citizenId,
                                                  @PathVariable(value = "reviewId") int reviewId){
        reviewService.deleteReview(citizenId, reviewId);
        return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
    }
}
