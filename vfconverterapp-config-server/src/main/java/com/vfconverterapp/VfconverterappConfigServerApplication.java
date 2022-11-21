package com.vfconverterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author SivakumarK
 *
 */
@SpringBootApplication
@EnableConfigServer
public class VfconverterappConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VfconverterappConfigServerApplication.class, args);
	}

}
