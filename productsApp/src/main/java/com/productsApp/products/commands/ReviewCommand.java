package com.productsApp.products.commands;

import com.productsApp.products.DTO.ReviewRequest;
import com.productsApp.products.queue.ReviewSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewCommand extends Command{

    @Autowired
    private ReviewSender reviewSender;

    @Override
    public void execute() {
        reviewSender.sendReviewRequest((ReviewRequest) request);
    }
}