package com.ty.tms.core.verticles.business;

import com.ty.tms.core.verticles.VertxApplication;
import io.vertx.core.eventbus.EventBus;

/**
 * 消费者
 */
@Deprecated
public class ConsumerVerticle extends VertxApplication {

    @Override
    public void start() {
        try {
            EventBus eventBus = VertxApplication.getEventBus();
            eventBus.consumer("key.requestId.Point.message", message -> {
                System.out.println(message.address());
                System.out.println(message.body());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
