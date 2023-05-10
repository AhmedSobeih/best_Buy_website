package com.productReviewsApp.reviews.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document("reviews")
public class Review {
    @Id
    private String id;
    private String model;
    private String userID;
    private String comment;
    private String transactionID;
    private Long likes;
    private Long dislikes;
    private double rating;
    private Date date;




}