package com.eatza.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableConfigServer
@SpringBootApplication
@RestController
public class SpringConfigServerApplication {


	@GetMapping("/test/server")
	public String serverUp() {
		return "Config server Up!!!";
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringConfigServerApplication.class, args);
	}

}
