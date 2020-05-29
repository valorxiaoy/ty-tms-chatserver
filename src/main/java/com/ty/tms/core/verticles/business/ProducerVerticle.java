package com.ty.tms.core.verticles.business;

import com.ty.tms.core.bean.MessageBody;
import com.ty.tms.core.verticles.base.RouterVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * 短信息生产者客户端
 */
public class ProducerVerticle extends RouterVerticle {

    @Override
    public void intiRouterVerticle() {
        this.ROUTE_PATH = "/send";
        this.handler = new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext routingContext) {
                try {
                    // TODO 发送消息到EventBus
                    EventBus eventBus = vertx.eventBus();
                    eventBus.send("key.requestId.Point.message", "Hello Consumer!");
                    eventBus.send("key.requestId.Point.message", "I'm God!");
                    eventBus.send("key.requestId.Point.message", "12");
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
