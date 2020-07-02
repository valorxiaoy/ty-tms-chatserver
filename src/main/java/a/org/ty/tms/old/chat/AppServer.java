package a.org.ty.tms.old.chat;

import a.org.ty.tms.old.chat.server.ChatServer;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class AppServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        NetServerOptions options = new NetServerOptions().setPort(8088);
        NetServer server = vertx.createNetServer(options);


        server.connectHandler(socket -> {
            // Handle the connection in here
            socket.handler(buffer -> {
                System.out.println(buffer.toString());
                System.out.println("I received some bytes: " + buffer.length());
            });
            // 回复消息
            socket.write(ChatServer.distributeSocketAddress());
        });

        server.listen(8088, "127.0.0.1", res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }
}
