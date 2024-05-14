package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Controller
@EnableWebFlux
@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private RedisService redisService;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@GetMapping("/gets")
	public ResponseEntity<Mono<String>> index(@RequestParam("key") String key) {
		return ResponseEntity.ok(redisService.getValue(key));
	}

	@GetMapping("/sets")
	public ResponseEntity<Mono<Boolean>> set(@RequestParam("key") String key, @RequestParam("value") String value) {
		return ResponseEntity.ok(redisService.setValue(key, value));
	}
	@GetMapping("/health/redis")
	public Mono<ResponseEntity<String>> checkRedisConnection() {
		return Mono.fromSupplier(() -> {
			try {
				redisConnectionFactory.getConnection();
				return ResponseEntity.ok().body("Redis connection successful");
			} catch (Exception e) {
				return ResponseEntity.status(500).body("Failed to connect to Redis");
			}
		});
	}



}
