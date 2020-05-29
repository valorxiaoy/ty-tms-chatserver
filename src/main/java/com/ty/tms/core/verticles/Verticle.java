package com.ty.tms.core.verticles;

import com.ty.tms.core.verticles.base.RedisVerticle;
import com.ty.tms.core.verticles.business.ConsumerRegisterVerticle;
import com.ty.tms.core.verticles.business.ConsumerVerticle;
import com.ty.tms.core.verticles.business.ProducerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

/**
 * 程序处理逻辑，调用其他 POJO/POGO。
 */
public class Verticle {

    public static void main(String[] args) {
        DeploymentOptions options = new DeploymentOptions().setInstances(4);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RedisVerticle());
        /*vertx.deployVerticle(new ProducerVerticle(), options);
        vertx.deployVerticle(new ConsumerRegisterVerticle(), options);
        vertx.deployVerticle(new ConsumerVerticle(), options);*/

        vertx.deployVerticle("com.ty.tms.core.verticles.business.ProducerVerticle", options);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ConsumerRegisterVerticle", options);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ConsumerVerticle", options);

        System.out.println("部署成功");
    }
}
