package com.productReviewsApp.reviews.controller;

import com.productReviewsApp.reviews.dto.ReviewRequest;
import com.productReviewsApp.reviews.dto.ReviewResponce;
import com.productReviewsApp.reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
