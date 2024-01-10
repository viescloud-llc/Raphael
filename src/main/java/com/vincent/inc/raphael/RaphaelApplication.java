package com.vincent.inc.raphael;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

import com.vincent.inc.viesspringutils.ViesApplication;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class RaphaelApplication extends ViesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaphaelApplication.class, args);
	}

	@Override
	public String getApplicationName() {
		return "Raphael";
	}
}
