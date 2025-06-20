package com.vincent.inc.raphael;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import com.viescloud.eco.viesspringutils.auto.config.ViesBeanConfig;


@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@Import(ViesBeanConfig.class)
@EnableJpaRepositories
@EntityScan
public class RaphaelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaphaelApplication.class, args);
	}
}
