package com.productReviewsApp.reviews.controller;

import com.productReviewsApp.reviews.dto.ReviewRequest;
import com.productReviewsApp.reviews.dto.ReviewResponce;
import com.productReviewsApp.reviews.model.Review;
import com.productReviewsApp.reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestBody ReviewRequest reviewRequest){
        reviewService.createReview(reviewRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponce> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @PutMapping
    public ResponseEntity updateReview(@RequestBody Review review){
        reviewService.updateReview(review);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/incLikes/{id}")
    public ResponseEntity updateReviewLikes(@PathVariable String id){
        reviewService.updateReviewLikes(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/incDislikes/{id}")
    public ResponseEntity updateReviewDislikes(@PathVariable String id){
        reviewService.updateReviewDislikes(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable String id){
        return ResponseEntity.ok(reviewService.findReviewById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable String id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
