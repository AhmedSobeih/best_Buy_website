package com.productReviewsApp.reviews.service;

import com.productReviewsApp.reviews.dto.ReviewRequest;
import com.productReviewsApp.reviews.dto.ReviewResponce;
import com.productReviewsApp.reviews.dto.ReviewRUDRequest;
import com.productReviewsApp.reviews.model.Review;
import com.productReviewsApp.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .dislikes(reviewRequest.getDislikes())
                .likes(reviewRequest.getLikes())
                .rating(reviewRequest.getRating())
                .model(reviewRequest.getModel())
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
                .userID(review.getUserID())
                .build();
    }

    public Review findReviewById(String id) {
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find a review by ID: %s", id)
        ));
    }
    public void deleteReview(String id){
        reviewRepository.deleteById(id);
    }

    public void updateReview(ReviewRUDRequest reviewRUDRequest){
        Review review = Review.builder().
                id(reviewRUDRequest.getId()).
                userID(reviewRUDRequest.getUserID()).
                model(reviewRUDRequest.getModel()).
                comment(reviewRUDRequest.getComment()).
                likes(reviewRUDRequest.getLikes()).
                dislikes(reviewRUDRequest.getDislikes()).
                rating(reviewRUDRequest.getRating()).
                date(reviewRUDRequest.getDate()).
                build();

        Review savedReview = reviewRepository.findById(review.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find a review by ID %s", review.getId())
                ));
        savedReview.setModel(review.getModel());
        savedReview.setUserID(review.getUserID());
        savedReview.setComment(review.getComment());
        savedReview.setLikes(review.getLikes());
        savedReview.setDislikes(review.getDislikes());
        savedReview.setRating(review.getRating());

        reviewRepository.save(savedReview);

    }

    public void updateReviewLikes(String id){
        Review savedReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find a review by ID %s", id)
                ));
        Long newLikes = savedReview.getLikes()+1;
        double newRating = calculateRatingOutOfFive(newLikes,savedReview.getDislikes());
        savedReview.setLikes(newLikes);
        savedReview.setRating(newRating);

        reviewRepository.save(savedReview);
    }

    public void updateReviewDislikes(String id){
        Review savedReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find a review by ID %s", id)
                ));
        Long newDislikes = savedReview.getDislikes()+1;
        double newRating = calculateRatingOutOfFive(savedReview.getLikes(),newDislikes);
        savedReview.setDislikes(newDislikes);
        savedReview.setRating(newRating);

        reviewRepository.save(savedReview);
    }

    private double calculateRatingOutOfFive(double likes, double dislikes){
        double rating = (likes/(likes+dislikes))*5;
        rating*=10;
        rating=Math.floor(rating);
        rating/=10;
        return rating;
    }
}
