package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ty.tms.core.bean.RequestParams;
import com.ty.tms.core.bean.ResponseBody;
import com.ty.tms.core.bean.po.GuardionInfo;
import com.ty.tms.core.service.GuardionInfoService;
import com.ty.tms.core.tools.ResponseCode;
import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.List;

public class GuardionInfoRoute extends RouterVerticle {

    private static Logger logger = Logger.getLogger(TeacherInfoRoute.class);

    private static GuardionInfoService guardionInfoService = new GuardionInfoService();

    public static void trigger() {
        // 查询监护人信息
        searchGuardionInfo("/guardian");
        // 查询所有监护人信息
        searchGuardionInfoAll("/guardians");
    }

    private static void searchGuardionInfo(String routePath) {
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                String result = null;
                try {
                    RequestParams params = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    GuardionInfo guardionInfo = JSONObject.parseObject(params.getParams().toString(), GuardionInfo.class);
                    guardionInfo = guardionInfoService.searchGuardionInfo(guardionInfo.getId());
                    result = new ResponseBody(ResponseCode.SUCCESS, "查询成功", guardionInfo).getJson();
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


    private static void searchGuardionInfoAll(String routePath) {
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                String result = null;
                try {
                    RequestParams params = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    GuardionInfo guardionInfo = JSONObject.parseObject(params.getParams().toString(), GuardionInfo.class);
                    PageHelper.startPage(params.getPageNum(), params.getPageSize());
                    List<GuardionInfo> list = guardionInfoService.searchGuardionInfoAll(guardionInfo);
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
