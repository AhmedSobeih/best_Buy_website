package com.productReviewsApp.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @Id
    private String id;
    private String model;
    private String userID;
    private String comment;
    private Long likes;
    private Long dislikes;
    private double rating;
    private Date date;

}