package com.productsApp.products.commands;

import com.productsApp.products.DTO.AddProductToStockRequest;
import com.productsApp.products.queue.StockSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockSenderCommand extends Command{

    @Autowired
    private StockSender stockSender;

    @Override
    public void execute() {
        stockSender.sendAddProductRequest((AddProductToStockRequest) request);
    }
}
