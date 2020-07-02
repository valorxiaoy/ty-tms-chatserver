package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.ty.tms.core.tools.MessageToken;
import com.ty.tms.core.verticles.VertxApplication;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Deprecated
public class SendMessageRoute extends VertxApplication {

    @Deprecated
    public static void trigger() {
        String routePath = "/send";
        Handler<RoutingContext> handler = routingContext -> {
            try {
                routingContext.request().bodyHandler(bodyHandler -> {
                    HttpServerRequest request = routingContext.request();
                    HttpServerResponse response = routingContext.response();

                    JSONObject jsonObject = JSONObject.parseObject(bodyHandler.toString());
                    String messageContent = jsonObject.get("message").toString();
                    // 接受学校ID
                    String schoolId = jsonObject.get("schoolId").toString();
                    // 接收用户ID
                    String userId = jsonObject.get("userId").toString();
                    // 获取用户队列令牌
                    String key = MessageToken.getToken(schoolId, userId);
                    EventBus eventBus = getEventBus();
                    /*eventBus.send("key.requestId.Point.message", messageContent);*/
                    // 发送消息到EventBus
                    eventBus.request(key, messageContent, replyHandler -> {
                        if (replyHandler.succeeded()) {
                            // TODO 如果成功接受并处理了消息以后，客户端会返回处理结果，应答模式
                            // TODO 如果发送成功，则...
                        } else {
                            // TODO 如果发送失败，则...
                        }
                    });

                });
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // 返回发送结果
                HttpServerResponse response = routingContext.response();
                response.putHeader("content-type", "text/plain");
                // 写入响应并结束处理
                response.end("信息发送成功");
            }
        };
        VertxApplication.addRoutePost(routePath, handler);
    }
}
