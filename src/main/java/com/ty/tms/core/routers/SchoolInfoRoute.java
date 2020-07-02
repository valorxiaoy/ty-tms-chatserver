package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.ty.tms.core.bean.RequestParams;
import com.ty.tms.core.bean.ResponseBody;
import com.ty.tms.core.bean.po.AccountInfo;
import com.ty.tms.core.bean.po.SchoolInfo;
import com.ty.tms.core.service.SchoolInfoService;
import com.ty.tms.core.tools.ResponseCode;
import com.ty.tms.core.verticles.base.business.service.RouterVerticle;
import com.ty.tms.core.verticles.base.service.RedisVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 用户信息
 */
public class SchoolInfoRoute extends RouterVerticle {


    private static final Logger logger = LogManager.getLogger(SchoolInfoRoute.class);

    private static SchoolInfoService schoolInfoService = new SchoolInfoService();
    private static String result = null;

    public static void trigger() {
        searchSchoolInfo("/school/info/:id");
        // searchSchoolInfos("/schools");
    }

    /**
     * 缓存学校信息
     * @param accountInfo
     */
    private static void setSchoolInfoByRedis(SchoolInfo accountInfo) {
        if (accountInfo != null) {
            String jsonBean = JSONObject.toJSONString(accountInfo);
            RedisAPI redisAPI = RedisVerticle.getRedisAPI();
            redisAPI.getset(accountInfo.getId().toString(), jsonBean, handler -> {
                if (handler.succeeded()) {
                    System.out.println("redis insert bean succeeded");
                } else {
                    System.out.println("redis insert bean failed");
                }
            });
        }
    }

    /**
     * 查询学校信息 by Code
     */
    private static void searchSchoolInfo(String routePath) {
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                try {
                    RequestParams params = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    final SchoolInfo schoolInfo = JSONObject.parseObject(params.getParams().toString(), SchoolInfo.class);
                    // 尝试从redis中获取
                    RedisAPI redisAPI = RedisVerticle.getRedisAPI();
                    if (schoolInfo != null && schoolInfo.getCode() != null && !"".equals(schoolInfo.getCode())) {
                        redisAPI.get(schoolInfo.getCode(), redisHandler -> {
                            String result = null;
                            try {
                                SchoolInfo info;
                                Response response = redisHandler.result();
                                if (redisHandler.succeeded() && response != null) {
                                    info = JSONObject.parseObject(response.toString(), SchoolInfo.class);
                                } else {
                                    // 否则在数据库中查询
                                    List<SchoolInfo> schoolInfos = schoolInfoService.searchSchoolInfoAll(schoolInfo);
                                    info = schoolInfos.size() > 0 ? schoolInfos.get(0) : null;
                                }
                                if (info != null) {
                                    setSchoolInfoByRedis(info);
                                    result = new ResponseBody(ResponseCode.SUCCESS, "查询成功", info).getJson();
                                } else {
                                    result = new ResponseBody(ResponseCode.DATA_NOT_FOUND, "查询失败", null).getJson();
                                }
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
                    } else {
                        logger.error("参数异常");
                        throw new Exception("参数异常");
                    }
                } catch (Exception ex) {
                    HttpServerResponse response = routingContext.response();
                    // 返回查询结果
                    response.putHeader("content-type", "application/json");
                    // 写入响应并结束处理
                    response.end(new ResponseBody(ResponseCode.BUSINESS_ERROR, "查询失败", null).getJson());
                }
            });
        };
        // 注册服务
        RouterVerticle.addRouteGet(routePath, handler);
    }
}
