package com.FAM.messageApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.FAM.messageApp.service.ChatService;



@SpringBootApplication
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@Slf4j
public class MessageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner1(ChatService chatRepository){
		return args -> {
//			Chat chat1 = new Chat("customer1", "rep1", "Chat 1", true);
//			Chat chat2 = new Chat("customer2", "rep2", "Chat 2", true);
//			Chat chat3 = new Chat("customer3", "rep3", "Chat 3", false);
			//System.out.println("insert chat... "+chat1 );
			//System.out.println(chatRepository.createChat("1","2"));
		};
	}
//	// Delete Chat Record using customerId
//	@Bean
//	CommandLineRunner runner2(ChatRepository chatRepository){
//		return args -> {
//			String customerId =  "customer1";
//			System.out.println("Delete chat with ID... "+customerId );
//			chatRepository.deleteChatById(customerId);
//		};
//	}
}