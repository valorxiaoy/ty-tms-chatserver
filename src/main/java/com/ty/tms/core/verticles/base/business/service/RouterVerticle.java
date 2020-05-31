package com.ty.tms.core.verticles.base.business.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * Verticle 基类
 */
public class RouterVerticle extends AbstractVerticle {

    private static Vertx vertx = Vertx.vertx();

    private static Router router = Router.router(vertx);

    public static EventBus getEventBus() {
        return vertx.eventBus();
    }

    public static void addRoute(String routePath, Handler<RoutingContext> handler) {
        router.route(HttpMethod.POST, routePath).handler(handler);
    }

    @Override
    public void start() {
        try {
            HttpClientOptions httpClientOptions = new HttpClientOptions();

            httpClientOptions.setReceiveBufferSize(1024000);
            httpClientOptions.setSendBufferSize(1024000);
            HttpServer server = vertx.createHttpServer();
            server.requestHandler(router);
            server.listen(8088);

            List<Route> routes = router.getRoutes();
            routes.forEach(bean -> {
                System.out.println("Router Path : " + bean.getPath());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
