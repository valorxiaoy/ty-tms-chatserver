package com.ty.tms.core.routers;

import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import com.ty.tms.core.verticles.base.service.RedisVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.RedisAPI;

/**
 * 消费者信息注册
 */
public class ConsumerRegisterRoute extends RouterVerticle {

    public static void trigger() {
        String routePaht = "/consumer/register";
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            HttpServerResponse response = routingContext.response();
            // 注册地址上报Redis
            RedisAPI redisAPI = RedisVerticle.getRedisAPI();
            try {
                // 消费者注册信息
                // TODO 完善注册信息组装流程
                /*String key = request.getParam("key");
                String value = request.getParam("value");*/
                String key = "TestKey1";
                String value = "TestValue1";
                redisAPI.keys("TestKey1", redisHandler -> {
                    if (redisHandler.succeeded()) {
                        if (redisHandler.result().size() > 0) {
                            System.out.println("Key 已存在，无需重复注册");
                        } else {
                            redisAPI.getset(key, value, handler1 -> {
                                if (handler1.succeeded()) {
                                    System.out.println("注册信息上报成功");
                                } else {
                                    System.out.println("注册信息上报失败");
                                }
                            });
                        }
                    } else {
                        System.out.println("Resdis connect error, Keys search error!");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // 返回注册结果
                /*Cookie cookie = Cookie.cookie("UserSecret", );
                response.addCookie(cookie);*/
                response.putHeader("content-type", "text/plain");
                // 写入响应并结束处理
                response.end("消费者信息注册成功");
            }
        };

        // 注册服务
        RouterVerticle.addRoute(routePaht, handler);
    }
}
