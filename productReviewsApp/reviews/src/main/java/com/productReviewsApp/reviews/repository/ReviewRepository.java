package com.productReviewsApp.reviews.repository;

import com.productReviewsApp.reviews.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review,String> {

}
