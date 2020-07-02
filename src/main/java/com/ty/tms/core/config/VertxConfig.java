package com.ty.tms.core.config;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class VertxConfig {

    private static Vertx vertx = Vertx.vertx();

    private static DeploymentOptions options = null;

    public void configOptions() {
        /*
        DeploymentOptions options = new DeploymentOptions();
        // 启用N个内核
        options.setInstances(1);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ProducerVerticle", options);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ConsumerRegisterVerticle", options);
        vertx.deployVerticle("com.ty.tms.core.verticles.business.ConsumerVerticle", options);
        */
    }

    public static void deployVerticle(Verticle verticle) {
        if (options != null) {
            vertx.deployVerticle(verticle, options);
        } else {
            vertx.deployVerticle(verticle);
        }
    }
}
