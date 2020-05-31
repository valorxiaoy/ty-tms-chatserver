package com.ty.tms.core.verticles.base.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;

/**
 * Redis服务
 */
public class RedisVerticle extends AbstractVerticle {

    private static RedisOptions redisOptions;

    private static RedisAPI redisAPI = null;

    static {
        redisOptions = new RedisOptions();
        redisOptions.setConnectionString("redis://:303772@122.114.21.155:6379/15");
    }

    public static RedisAPI getRedisAPI() {
        return redisAPI;
    }

    @Override
    public void start() throws Exception {
        Redis.createClient(vertx, redisOptions).connect(onConnect -> {
            if (onConnect.succeeded()) {
                RedisConnection client = onConnect.result();
                redisAPI = RedisAPI.api(client);
                System.out.println("Redis register succeeded!");
            }
        });
    }
}
