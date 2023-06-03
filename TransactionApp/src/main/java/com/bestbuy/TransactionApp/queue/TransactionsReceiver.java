package com.bestbuy.TransactionApp.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bestbuy.TransactionApp.dto.StockRequest;
import com.bestbuy.TransactionApp.service.ShoppingCartService;
import com.bestbuy.TransactionApp.service.StockService;

import lombok.RequiredArgsConstructor;

import javax.sound.midi.Receiver;

@Component
@RequiredArgsConstructor
public class TransactionsReceiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final ShoppingCartService shoppingCartService;
    private final StockService stockService;

    @RabbitListener(queues = "${rabbitmq.queues.transactions.name}")
    public String handle(String message){
        /*  Possible messages:
         *  1. user_created;userId
         *  2. product_created;productId,price,quantity
         *  3. product_updated;productId,price,quantity
         */
        logger.info("received message: " + message);

        try{
            String[] messageArray = message.split(";");
            switch(messageArray[0]){
                case "user_created": return createShoppingCart(messageArray[1]);
                case "product_created": return createStock(messageArray[1]);
                case "product_updated": return updateStock(messageArray[1]);
                default: return "ERROR";
            }
        } catch(Exception e){
            return "ERROR";
        }
    }

    public String updateStock(String str){
        try{
            String[] strArr = str.split(",");

            stockService.updateStock(new StockRequest(strArr[0], Double.parseDouble(strArr[1]), Integer.parseInt(strArr[2])));

            return "Stock Created Successfully!";
        } catch(Exception e){
            return "ERROR";
        }
    }

    public String createStock(String str){
        try{
            String[] strArr = str.split(",");

            stockService.createStock(strArr[0], Double.parseDouble(strArr[1]), Integer.parseInt(strArr[2]));

            return "Stock Created Successfully!";
        } catch(Exception e){
            return "ERROR";
        }
    }

    public String createShoppingCart(String userId){
        try{
            shoppingCartService.createShoppingCart(Long.parseLong(userId));
            return "User Shopping Cart Created successfully!";
        } catch(Exception e){
            return "ERROR";
        }
    }
}
