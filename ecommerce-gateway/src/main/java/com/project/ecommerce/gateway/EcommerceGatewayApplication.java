package com.project.ecommerce.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ComponentScans({@ComponentScan("com.project.ecommerce.logging"), @ComponentScan("com.project.ecommerce.gateway")})
public class EcommerceGatewayApplication {

	public static void main(String[] args) {
		// helps in logging of traceid and spanid which are created at gateway
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(EcommerceGatewayApplication.class, args);
	}

}
