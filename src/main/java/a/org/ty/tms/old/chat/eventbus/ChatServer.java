package a.org.ty.tms.old.chat.eventbus;

import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * 聊天服务器实现
 */
public class ChatServer {

    private static EventBus chatEventBus;

    public ChatServer(String consumerAddress) {
        this.chatEventBus = ChatServerConfig.getChatEventBus();
        chatConsumer(consumerAddress);
    }

    /**
     * 注册消息处理程序
     */
    private void chatConsumer(String consumerAddress) {
        // 获得消费者对象
        MessageConsumer<Object> consumer = chatEventBus.consumer(consumerAddress);
        // 设置读取流时的异常处理器
        consumer.exceptionHandler(ChatServer::chatMessageReadExceptionHandler);
        // 设置消息处理器
        consumer.handler(ChatServer::chatMessageHandler);
    }

    /**
     * 消息读取时异常处理器
     * @param throwable
     */
    private static void chatMessageReadExceptionHandler(Throwable throwable) {
        // TODO 设计异常处理机制
        throwable.printStackTrace();
    }

    /**
     * 聊天消息处理器
     * @param message
     */
    private static void chatMessageHandler(Message<Object> message) {
        // 消息发送的地址
        String address = message.address();
        // 消息发送的正文
        Object body = message.body();
        // 消息的头部信息
        MultiMap headers = message.headers();
        // 回复此消息
        message.reply(null);
        // 获取回复地址
        String replyAddress = message.replyAddress();
        System.out.println(body.toString());
    }
}
