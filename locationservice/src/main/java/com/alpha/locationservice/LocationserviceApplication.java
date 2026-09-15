package com.alpha.locationservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LocationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationserviceApplication.class, args);
		
	}
	
		@Bean			
	public RestTemplate getRestTemplate(){
		return new RestTemplate() ;

		
	}
		


}
