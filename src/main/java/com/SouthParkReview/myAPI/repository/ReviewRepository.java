package com.SouthParkReview.myAPI.repository;

import com.SouthParkReview.myAPI.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByCitizenId(int citizenId);
}
