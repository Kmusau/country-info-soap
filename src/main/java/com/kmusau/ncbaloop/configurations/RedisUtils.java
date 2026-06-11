package com.kmusau.ncbaloop.configurations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@AllArgsConstructor
@Component
@Slf4j
public class RedisUtils {
    private final RedisTemplate<String, String> redisTemplate;

    public String fetchFromRedis(String redisKey) {
        try {
           return redisTemplate.opsForValue().get(redisKey);
        } catch (Exception e) {
            log.info("Exception occurred while fetching from Redis => {}", e.getMessage());
            return null;
        }
    }

    public boolean writeToRedis(String key, String value, Duration expiresIn) {
        log.info("RedisKey => {} Value => {}", key, value);
        try {
            redisTemplate.opsForValue().set(key, value, expiresIn);
            log.info("Successfully cached Value ");
            return true;
        } catch (Exception ex) {
            log.info("Write to Redis Exceptions => {}", ex.getMessage());
            return false;
        }
    }

}
