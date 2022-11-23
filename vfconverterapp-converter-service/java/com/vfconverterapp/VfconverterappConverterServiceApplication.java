package com.vfconverterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;

/**
 * @author SivakumarK
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@PropertySource("classpath:application.yml")
public class VfconverterappConverterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VfconverterappConverterServiceApplication.class, args);
	}
	
}
