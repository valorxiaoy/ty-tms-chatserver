package com.ty.tms.old.message.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class ChatVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        NetServerOptions netServerOptions = new NetServerOptions();
        netServerOptions.setPort(8088);
        NetServer netServer = vertx.createNetServer(netServerOptions);
        netServer.listen();
        netServer.connectHandler(netSocket -> {
           netSocket.handler(handler -> {
               System.out.println(handler.toString());
           });
        });
    }
}
