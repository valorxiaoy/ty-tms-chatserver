package com.ty.tms.core.verticles;

import com.ty.tms.core.config.VertxConfig;
import com.ty.tms.core.routers.AccountInfoRoute;
import com.ty.tms.core.routers.SchoolInfoRoute;
import com.ty.tms.core.routers.SockJSRoute;
import com.ty.tms.core.verticles.business.RedisVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
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
 * 程序处理逻辑，调用其他 POJO/POGO。
 */
public class VertxApplication extends AbstractVerticle {

    private static Logger logger = Logger.getLogger(VertxApplication.class);

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

    public static void main() {
        init();
        businessInit();
        serviceInit();
    }

    public static void init() {
        // 初始化业务
        businessInit();
        // 发布服务
        serviceInit();
        // LOG
        logger.info("服务部署成功");
    }

    /**
     * 业务初始化
     */
    public static void businessInit() {
        // 消息通信功能
        SockJSRoute.trigger();
        // 学校信息服务功能
        SchoolInfoRoute.trigger();
        // 教师信息服务功能
        // TeacherInfoRoute.trigger();
        // 账号信息服务功能
        AccountInfoRoute.trigger();
    }

    /**
     * 服务初始化
     */
    public static void serviceInit() {
        // 发布Redis服务
        VertxConfig.deployVerticle(new RedisVerticle());
    }

    public void start(Promise<Void> startPromise) {
        try {
            main();
            HttpClientOptions httpClientOptions = new HttpClientOptions();

            httpClientOptions.setReceiveBufferSize(1024000);
            httpClientOptions.setSendBufferSize(1024000);
            HttpServer server = vertx.createHttpServer();
            // 限制只处理raw格式的json请求
            // router.route().handler(BodyHandler.create());
            // router.route().handler(CorsHandler.create("vertx\\.io").allowedMethod(HttpMethod.GET));
            /*router.route().handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET));
            router.route().handler(CorsHandler.create("*").allowedMethod(HttpMethod.POST));*/

            server.requestHandler(router);
            server.listen(8088, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    logger.info("HTTP server started on port 8088");
                } else {
                    startPromise.fail(http.cause());
                    logger.info("HTTP server started fail on port 8088");
                }
            });
            // 输出所有的服务地址
            router.getRoutes().stream().forEach(bean -> {
                logger.debug("Router Path : " + bean.getPath());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
