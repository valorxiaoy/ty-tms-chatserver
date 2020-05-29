package com.ty.tms.core.verticles.base;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
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
public abstract class RouterVerticle extends AbstractVerticle {

    protected String ROUTE_PATH;

    protected Handler<RoutingContext> handler;

    public abstract void intiRouterVerticle();

    @Override
    public void start() {
        try {
            HttpClientOptions httpClientOptions = new HttpClientOptions();

            httpClientOptions.setReceiveBufferSize(1024000);
            httpClientOptions.setSendBufferSize(1024000);
            HttpServer server = vertx.createHttpServer();
            intiRouterVerticle();
            Router router = Router.router(vertx);
            Route route = router.route(HttpMethod.POST, ROUTE_PATH);
            route.handler(handler);

            server.requestHandler(router::accept);
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
