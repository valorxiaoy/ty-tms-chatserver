package com.ty.tms.core.verticles.business;

import com.ty.tms.core.verticles.base.RedisVerticle;
import com.ty.tms.core.verticles.base.RouterVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;

/**
 * 消费者信息注册中心
 */
public class ConsumerRegisterVerticle extends RouterVerticle {
    @Override
    public void intiRouterVerticle() {
        this.ROUTE_PATH = "/consumer/register";
        this.handler = new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext routingContext) {
                try {
                    HttpServerRequest request = routingContext.request();
                    // 消费者注册信息
                    // TODO 完善注册信息组装流程
                    String key = request.getParam("key");
                    String value = request.getParam("value");
                    // 注册地址上报Redis
                    RedisAPI redisAPI = RedisVerticle.getRedisAPI();
                    redisAPI.getset(key, value, handler -> {
                        if (handler.succeeded()) {
                            System.out.println("注册信息上报成功");
                            Response result = handler.result();
                        } else {
                            System.out.println("注册信息上报失败");
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 返回注册结果
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "text/plain");
                    // 写入响应并结束处理
                    response.end("消费者信息注册成功");
                }
            }
        };
    }
}
