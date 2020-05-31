package com.ty.tms.core.verticles;

import com.ty.tms.core.routers.ConsumerRegisterRoute;
import com.ty.tms.core.routers.SendMessageRoute;
import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import com.ty.tms.core.verticles.base.service.RedisVerticle;
import com.ty.tms.core.verticles.business.ConsumerVerticle;
import io.vertx.core.Vertx;

/**
 * 程序处理逻辑，调用其他 POJO/POGO。
 */
public class Verticle {

    public static void main(String[] args) {
        // 初始化业务
        businessInit();
        Vertx vertx = Vertx.vertx();
        // 发布服务
        vertx.deployVerticle(new RedisVerticle());
        vertx.deployVerticle(new RouterVerticle());
        vertx.deployVerticle(new ConsumerVerticle());

        /*
        DeploymentOptions options = new DeploymentOptions();
        // 启用N个内核
        options.setInstances(1);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ProducerVerticle", options);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ConsumerRegisterVerticle", options);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ConsumerVerticle", options);
        */

        System.out.println("部署成功");
    }

    public static void businessInit() {
        ConsumerRegisterRoute.trigger();
        SendMessageRoute.trigger();
    }
}
