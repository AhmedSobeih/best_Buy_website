package com.productsApp.products.commands;

import com.productsApp.products.DTO.AuthRequest;
import com.productsApp.products.DTO.Request;
import com.productsApp.products.queue.StockSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockSender extends Command{

    @Autowired
    private StockSender stockSender;

    @Override
    public void execute() {
        stockSender.sendAddProductRequest((AuthRequest) request);
    }
}
