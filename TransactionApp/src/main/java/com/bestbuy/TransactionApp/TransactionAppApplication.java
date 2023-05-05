package com.bestbuy.TransactionApp;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Transactions App API Docs"))
public class TransactionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionAppApplication.class, args);
	}

	@Profile("!dev")
    @Bean
    public GroupedOpenApi groupedPublicOpenApi10() {
        return GroupedOpenApi
            .builder()
            .addOpenApiCustomizer(openApi -> openApi.getInfo().setVersion("v1"))
            .group("v1")
            .pathsToMatch("/api/v1/**")
            .displayName("v1")
            .build();
    }

}
