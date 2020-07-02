package a.org.ty.tms.old.chat.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;

/**
 * 聊天服务器配置
 */
public class ChatServerConfig {

    private static Vertx chatVertx;
    private static EventBus chatEventBus;

    /**
     * 初始化配置
     */
    public ChatServerConfig() {
        VertxOptions vertxOptions = new VertxOptions();
        EventBusOptions eventBusOptions = new EventBusOptions();
        eventBusOptions.setHost("127.0.0.1");
        eventBusOptions.setPort(8089);
        vertxOptions.setEventBusOptions(eventBusOptions);
        chatVertx = Vertx.vertx(vertxOptions);
        chatEventBus = chatVertx.eventBus();
        chatVertx.createHttpServer().listen();
    }

    private static void initVertxConfig() throws Exception {
    }

    /**
     * 获取ChatEventBus
     * @return
     */
    public static EventBus getChatEventBus() {
        return chatEventBus;
    }

}
