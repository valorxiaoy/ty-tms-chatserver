package com.ty.tms.core.tools;

/**
 * 组装消息服务令牌
 */
public class MessageToken {
    public static String getToken(String... args) {

        return "" + args.hashCode();
    }
}
