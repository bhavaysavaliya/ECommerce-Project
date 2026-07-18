package com.project.ecommerce.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class EcommerceGatewayApplication {

	public static void main(String[] args) {
		// helps in logging of traceid and spanid which are created at gateway
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(EcommerceGatewayApplication.class, args);
	}

}
