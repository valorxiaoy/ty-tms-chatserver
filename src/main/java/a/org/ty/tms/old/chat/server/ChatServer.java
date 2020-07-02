package a.org.ty.tms.old.chat.server;

import com.alibaba.fastjson.JSONObject;
import a.org.ty.tms.old.chat.bean.SocketAddressBean;
import io.vertx.core.json.JsonObject;

/**
 * 图文短消息服务
 */
public class ChatServer {

    /**
     * 分配通信地址
     * @return
     */
    public static String distributeSocketAddress() {

        SocketAddressBean socketAddressBean = new SocketAddressBean();
        socketAddressBean.setId("I'm id!");

        JsonObject socketAddress = new JsonObject();
        socketAddress.put("Key", "key");
        socketAddress.put("RequestId", "requestId");
        socketAddress.put("SocketAddressBean", JSONObject.toJSONString(socketAddressBean));

        return socketAddress.toString();
    }
}
