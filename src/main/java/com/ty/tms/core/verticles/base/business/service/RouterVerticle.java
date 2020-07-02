package com.ty.tms.core.verticles.base.business.service;

import com.ty.tms.core.verticles.base.service.RedisVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.apache.log4j.Logger;

/**
 * Verticle 基类
 */
public class RouterVerticle extends AbstractVerticle {

    private static Logger logger = Logger.getLogger(RouterVerticle.class);

    private static Vertx vertx = Vertx.vertx();

    private static Router router = Router.router(vertx);

    protected static EventBus getEventBus() {
        return vertx.eventBus();
    }

    protected static SockJSHandler getSockJSHandler() {return SockJSHandler.create(vertx);}

    protected static void addRoutePost(String routePath, Handler<RoutingContext> handler) {
        router.route(HttpMethod.POST, routePath).consumes("*/json").handler(handler);
    }

    protected static void addRouteGet(String routePath, Handler<RoutingContext> handler) {
        router.route(HttpMethod.GET, routePath).consumes("*/json").handler(handler);
    }

    @Override
    public void start() {
        try {
            HttpClientOptions httpClientOptions = new HttpClientOptions();

            httpClientOptions.setReceiveBufferSize(1024000);
            httpClientOptions.setSendBufferSize(1024000);
            HttpServer server = vertx.createHttpServer();
            // 限制只处理raw格式的json请求
            // router.route().handler(BodyHandler.create());
            server.requestHandler(router);
            server.listen(8088);
            // 输出所有的服务地址
            router.getRoutes().stream().forEach(bean -> {
                logger.debug("Router Path : " + bean.getPath());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
