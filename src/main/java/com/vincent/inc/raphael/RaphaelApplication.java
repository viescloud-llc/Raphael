package com.vincent.inc.raphael;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class RaphaelApplication {

	@Value("${spring.profiles.active}")
	private String env = "?";

	public static void main(String[] args) {
		SpringApplication.run(RaphaelApplication.class, args);
	}

	@GetMapping()
	public String healthCheck1() {
		return String.format("Raphael %s is up and running", env);
	}

	@GetMapping("/_status/healthz")
	public String healthCheck2() {
		return String.format("Raphael %s is up and running", env);
	}

}
