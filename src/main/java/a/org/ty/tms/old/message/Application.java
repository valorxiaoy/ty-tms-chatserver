package a.org.ty.tms.old.message;


import a.org.ty.tms.old.message.verticle.ChatVerticle;
import io.vertx.core.Vertx;

public class Application {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(ChatVerticle.class.getName());
    }
}
