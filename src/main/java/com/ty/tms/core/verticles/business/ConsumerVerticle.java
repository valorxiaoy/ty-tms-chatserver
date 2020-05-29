package com.ty.tms.core.verticles.business;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * 消费者
 */
public class ConsumerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        try {
            EventBus eventBus = vertx.eventBus();
            eventBus.consumer("key.requestId.Point.message", message -> {
                System.out.println(message.address());
                System.out.println(message.body());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
