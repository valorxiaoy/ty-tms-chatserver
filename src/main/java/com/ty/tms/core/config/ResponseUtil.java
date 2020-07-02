package com.ty.tms.core.config;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class ResponseUtil {

    public static void success(RoutingContext routingContext, Object object) {
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encodePrettily(object));
        routingContext.response().close();
    }

    public static void failedService(RoutingContext routingContext, Object object) {
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encodePrettily(object));
        routingContext.response().close();
    }

    public static void failedServer(RoutingContext routingContext, Integer statusCode) {
        routingContext.response()
                .setStatusCode(statusCode)
                .putHeader("content-type", "application/json")
                .end();
        routingContext.response().close();
    }
}