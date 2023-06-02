package com.productReviewsApp.reviews.queue;



import com.productReviewsApp.reviews.dto.ReviewResponce;
import com.productReviewsApp.reviews.service.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewsReceiver {

    private final ReviewService reviewService;

    public ReviewsReceiver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.review.name}")
    public List<ReviewResponce> receiveRequest()
    {
        return handleMessage();
    }
     public List<ReviewResponce> handleMessage(){
            return reviewService.getAllReviews();
     }
}