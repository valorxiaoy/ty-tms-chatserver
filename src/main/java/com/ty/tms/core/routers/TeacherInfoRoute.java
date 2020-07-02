package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ty.tms.core.bean.RequestParams;
import com.ty.tms.core.bean.ResponseBody;
import com.ty.tms.core.bean.po.AccountInfo;
import com.ty.tms.core.bean.po.TeacherInfo;
import com.ty.tms.core.service.TeacherInfoService;
import com.ty.tms.core.tools.ResponseCode;
import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.List;

public class TeacherInfoRoute extends RouterVerticle {

    private static Logger logger = Logger.getLogger(TeacherInfoRoute.class);

    private static TeacherInfoService teacherInfoService = new TeacherInfoService();

    public static void trigger() {
        searchTeacherInfoAll("/school/teacher");
    }

    /**
     * 查询学校所有老师信息
     */
    private static void searchTeacherInfoAll(String routePath) {
        /*
        // 此方法仅限于使用Raw格式发送参数
        // 配合RouterVerticle类中的router.route().handler(BodyHandler.create());语句一起使用
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.bodyHandler(bodyHandler -> {
                String result = null;
                try {
                    TeacherInfo teacherInfo = JSONObject.parseObject(bodyHandler.toString(), TeacherInfo.class);
                    List<TeacherInfo> list = teacherInfoService.searchTeacherInfoAll(teacherInfo);
                    result = new ResponseBody("200", "查询成功", list).getJson();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    result = new ResponseBody("500", "查询失败", null).getJson();
                } finally {
                    HttpServerResponse response = routingContext.response();
                    // 返回查询结果
                    response.putHeader("content-type", "application/json");
                    // 写入响应并结束处理
                    response.end(result);
                }
            });
        };
        */

        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                String result = null;
                try {
                    RequestParams requestParams = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    TeacherInfo teacherInfo = JSONObject.parseObject(requestParams.getParams().toString(), TeacherInfo.class);

                    PageHelper.startPage(requestParams.getPageNum(), requestParams.getPageSize());
                    List<TeacherInfo> list = teacherInfoService.searchTeacherInfoAll(teacherInfo);
                    PageInfo pageInfo = new PageInfo(list);
                    result = new ResponseBody(ResponseCode.SUCCESS, "查询成功", pageInfo).getJson();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                    result = new ResponseBody(ResponseCode.BUSINESS_ERROR, "查询失败", null).getJson();
                } finally {
                    HttpServerResponse response = routingContext.response();
                    // 返回查询结果
                    response.putHeader("content-type", "application/json");
                    // 写入响应并结束处理
                    response.end(result);
                }
            });
        };

        // 注册服务
        RouterVerticle.addRoutePost(routePath, handler);
    }
}
