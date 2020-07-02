package com.ty.tms.core.verticles.business;

import io.vertx.core.AbstractVerticle;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import org.apache.log4j.Logger;

/**
 * Redis服务
 */
public class RedisVerticle extends AbstractVerticle {

    private static Logger logger = Logger.getLogger(RedisVerticle.class);

    private static RedisOptions redisOptions;

    private static RedisAPI redisAPI = null;

    static {
        redisOptions = new RedisOptions();
        // redisOptions.setConnectionString("redis://:303772@122.114.21.155:6379/15");
        redisOptions.setConnectionString("redis://:123123@127.0.0.1:6379/15");
    }

    public static RedisAPI getRedisAPI() {
        return redisAPI;
    }

    @Override
    public void start() throws Exception {
        Redis redis = Redis.createClient(vertx, redisOptions);
        redis.connect(onConnect -> {
            if (onConnect.succeeded()) {
                RedisConnection client = onConnect.result();
                redisAPI = RedisAPI.api(client);
                logger.info("Redis connect succeeded!");
            } else {
                logger.error("Redis connect fail!");
            }
        });
    }
}
