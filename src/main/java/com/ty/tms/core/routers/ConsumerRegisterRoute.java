package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.ty.tms.core.tools.MessageToken;
import com.ty.tms.core.verticles.VertxApplication;
import com.ty.tms.core.verticles.business.RedisVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.RedisAPI;

/**
 * 消费者信息注册
 */
@Deprecated
public class ConsumerRegisterRoute extends VertxApplication {

    @Deprecated
    public static void trigger() {
        String routePath = "/consumer/register";

        Handler<RoutingContext> handler = routingContext ->{
            HttpServerRequest request = routingContext.request();
            HttpServerResponse response = routingContext.response();
            // 接受学校ID
            String schoolId = request.getParam("schoolId");
            // 接收用户ID
            String userId = request.getParam("userId");
            // 组装队列令牌
            String key = MessageToken.getToken(schoolId, userId);
            // 消费者注册信息
            EventBus eventBus = getEventBus();
            MessageConsumer<Object> consumer = eventBus.consumer("");
            consumer.handler(message -> {
                // TODO 处理消息
                System.out.println(message.body());
            });
            consumer.exceptionHandler(throwable -> {
                // TODO 将消息存入数据库
                // messageContent
            });
        };

        // 注册服务
        VertxApplication.addRoutePost(routePath, handler);
    }

    @Deprecated
    public static void trigger2() {
        String routePaht = "/consumer/register";
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            HttpServerResponse response = routingContext.response();
            // 注册地址上报Redis
            RedisAPI redisAPI = RedisVerticle.getRedisAPI();
            try {
                // 消费者注册信息
                // 接受学校ID
                String schoolId = request.getParam("schoolId");
                // 接收用户ID
                String userId = request.getParam("userId");
                // 组装消息服务令牌
                String key = MessageToken.getToken(schoolId, userId);
                JSONObject value = new JSONObject();
                value.put("schoolId", schoolId);
                value.put("userId", userId);
                redisAPI.keys(key, redisHandler -> {
                    if (redisHandler.succeeded()) {
                        if (redisHandler.result().size() > 0) {
                            System.out.println("Key 已存在，无需重复注册");
                        } else {
                            redisAPI.getset(key, value.toJSONString(), handler1 -> {
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
        VertxApplication.addRoutePost(routePaht, handler);
    }
}
