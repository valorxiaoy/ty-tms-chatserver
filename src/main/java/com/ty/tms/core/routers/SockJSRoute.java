package com.ty.tms.core.routers;

import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class SockJSRoute extends RouterVerticle {

    public static void trigger() {
        String routePath = "/eventbus/*";

        // 允许接收消息
        PermittedOptions inboundPermitted = new PermittedOptions().setAddressRegex("^tms-client.*");
        // 允许发送消息
        PermittedOptions outboundPermitted = new PermittedOptions().setAddressRegex("^tms-client.*");

        SockJSBridgeOptions options = new SockJSBridgeOptions();
        options.addInboundPermitted(inboundPermitted);
        options.addOutboundPermitted(outboundPermitted);

        SockJSHandler sockJSHandler = getSockJSHandler();
        sockJSHandler.bridge(options/*, handler -> {
            JsonObject rawMessage = handler.getRawMessage();

            System.out.println("AAA");
            EventBus eventBus = getEventBus();
            // eventBus.request("");
        }*/);

        RouterVerticle.addRouteGet(routePath, sockJSHandler);

    }
}
