package com.vfconverterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author SivakumarK
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class VfconverterappGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VfconverterappGatewayServerApplication.class, args);
	}
	
	@Bean
	public RouteLocator customRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
		.route("ConverterService",ps->ps.path("/vfconverter-api/**").uri("lb://CONVERTER-SERVICE"))
		.build();
	}
	
}
