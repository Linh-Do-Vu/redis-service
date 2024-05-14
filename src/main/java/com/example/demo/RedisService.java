package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedisService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Autowired
    public RedisService(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Boolean> setValue(String key, String value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }

    public Mono<String> getValue(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }
}