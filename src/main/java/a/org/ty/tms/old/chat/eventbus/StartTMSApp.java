package a.org.ty.tms.old.chat.eventbus;

/**
 * 启动即时通讯程序
 */
public class StartTMSApp {

    public static void main(String[] args) {
        // 初始化配置
        ChatServerConfig chatServerConfig = new ChatServerConfig();
        // 启动消息消费者
        ChatServer chatServer = new ChatServer("com.ty.key.request.random");

    }
}
