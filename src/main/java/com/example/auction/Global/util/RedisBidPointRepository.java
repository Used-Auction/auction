package com.example.auction.Global.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class RedisBidPointRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisBidPointRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setBidPoint(Long auctionId , String auctionData){
        HashOperations<String, Long, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("auction", auctionId , auctionData);
        log.info("{}:{}", auctionId,hashOperations.get("auction", auctionId));
    }

    public String getAuctionData(Long auctionId){
        HashOperations<String, Long, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get("auction", auctionId );
    }

}
