package com.ty.tms.core.verticles;

import com.ty.tms.core.config.VertxConfig;
import com.ty.tms.core.routers.AccountInfoRoute;
import com.ty.tms.core.routers.SchoolInfoRoute;
import com.ty.tms.core.routers.SockJSRoute;
import com.ty.tms.core.routers.TeacherInfoRoute;
import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import com.ty.tms.core.verticles.base.service.RedisVerticle;
import org.apache.log4j.Logger;

/**
 * 程序处理逻辑，调用其他 POJO/POGO。
 */
public class VertxApplication {

    private static Logger logger = Logger.getLogger(VertxApplication.class);

    public static void main(String[] args) {
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
        TeacherInfoRoute.trigger();
        // 账号信息服务功能
        AccountInfoRoute.trigger();
    }

    /**
     * 服务初始化
     */
    public static void serviceInit() {
        VertxConfig.deployVerticle(new RedisVerticle());
        VertxConfig.deployVerticle(new RouterVerticle());
    }
}
