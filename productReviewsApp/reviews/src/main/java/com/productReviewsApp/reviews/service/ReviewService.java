package com.productReviewsApp.reviews.service;

import com.productReviewsApp.reviews.dto.ReviewRequest;
import com.productReviewsApp.reviews.dto.ReviewResponce;
import com.productReviewsApp.reviews.model.Review;
import com.productReviewsApp.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void createReview(ReviewRequest reviewRequest){
        Review review= Review.builder()
                .id(reviewRequest.getId())
                .comment(reviewRequest.getComment())
                .date(reviewRequest.getDate())
                .dislikes(reviewRequest.getDislikes())
                .likes(reviewRequest.getLikes())
                .rating(reviewRequest.getRating())
                .model(reviewRequest.getModel())
                .transactionID(reviewRequest.getTransactionID())
                .userID(reviewRequest.getUserID())
                .build();

        reviewRepository.save(review);
    }

    public List<ReviewResponce> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::mapToReviewResponce).toList();
    }

    private ReviewResponce mapToReviewResponce(Review review) {
        return ReviewResponce.builder()
                .id(review.getId())
                .comment(review.getComment())
                .date(review.getDate())
                .dislikes(review.getDislikes())
                .likes(review.getLikes())
                .rating(review.getRating())
                .model(review.getModel())
                .transactionID(review.getTransactionID())
                .userID(review.getUserID())
                .build();
    }
}
