package com.vfconverterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author SivakumarK
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class vfconverterappEurekaRegisteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(vfconverterappEurekaRegisteryApplication.class, args);
	}

}
