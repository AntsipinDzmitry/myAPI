package com.SouthParkReview.myAPI.repository;

import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    Optional<Citizen> findByDescription(String description);
}
