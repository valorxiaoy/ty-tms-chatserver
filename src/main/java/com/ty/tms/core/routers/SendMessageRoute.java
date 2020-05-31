package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class SendMessageRoute extends RouterVerticle {

    public static void trigger() {
        String routePaht = "/send";
        Handler<RoutingContext> handler = routingContext -> {
            try {
                routingContext.request().bodyHandler(bodyHandler -> {
                    JSONObject jsonObject = JSONObject.parseObject(bodyHandler.toString());
                    String message = jsonObject.get("message").toString();
                    // TODO 发送消息到EventBus
                    EventBus eventBus = getEventBus();
                    eventBus.send("key.requestId.Point.message", message);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // 返回注册结果
                HttpServerResponse response = routingContext.response();
                response.putHeader("content-type", "text/plain");
                // 写入响应并结束处理
                response.end("信息发送成功");
            }
        };
        RouterVerticle.addRoute(routePaht, handler);
    }
}
