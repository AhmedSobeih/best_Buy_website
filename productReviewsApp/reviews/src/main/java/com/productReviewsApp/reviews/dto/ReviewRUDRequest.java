package com.productReviewsApp.reviews.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

// TODO: Check design pattern for Request classes

@Data
public class ReviewRUDRequest extends ReviewRequest{
    @Id
    private String id;
}
