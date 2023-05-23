package com.SouthParkReview.myAPI.repository;

import com.SouthParkReview.myAPI.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewRepositoryTests(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void ReviewRepository_SaveReview_ReturnReviewIsNotNull() {
        Review review = Review.builder().content("norm").title("Mr").stars(4).build();
        Review savedReview = reviewRepository.save(review);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAllReviews_ReturnsMoreThenOneReviews(){
        Review review = Review.builder().content("norm").title("Mr").stars(4).build();
        Review review2 = Review.builder().content("norm").title("Mr").stars(4).build();

        reviewRepository.save(review);
        reviewRepository.save(review2);
        List<Review> reviews = reviewRepository.findAll();

        Assertions.assertThat(reviews).isNotNull();
        Assertions.assertThat(reviews.size()).isEqualTo(2);
    }
    @Test
    public void ReviewRepository_FindReviewById_ReturnReviewIsNotNull() {
        Review review = Review.builder().content("norm").title("Mr").stars(4).build();
        Review savedReview = reviewRepository.save(review);

        Review returndReview = reviewRepository.findById(savedReview.getId()).get();

        Assertions.assertThat(returndReview).isNotNull();
    }
    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder().content("norm").title("Mr").stars(4).build();
        Review savedReview = reviewRepository.save(review);

        Review returndReview = reviewRepository.findById(savedReview.getId()).get();
        returndReview.setTitle("newTitle");
        returndReview.setContent("newContent");
        Review updatedReview = reviewRepository.save(returndReview);

        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
        Assertions.assertThat(updatedReview.getContent()).isNotNull();
    }
    @Test
    public void ReviewRepository_DeleteReview_DeletedReviewIsNull() {
        Review review = Review.builder().content("norm").title("Mr").stars(4).build();
        Review savedReview = reviewRepository.save(review);

        Review returnedReview = reviewRepository.findById(savedReview.getId()).get();
        reviewRepository.deleteById(returnedReview.getId());
        Optional<Review> deletedReview = reviewRepository.findById(returnedReview.getId());

        Assertions.assertThat(deletedReview).isEmpty();

    }

}
