package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ty.tms.core.bean.RequestParams;
import com.ty.tms.core.bean.ResponseBody;
import com.ty.tms.core.bean.vo.UserBuddyVO;
import com.ty.tms.core.service.UserBuddyService;
import com.ty.tms.core.tools.ResponseCode;
import com.ty.tms.core.verticles.VertxApplication;
import com.ty.tms.core.verticles.business.RedisVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 用户好友
 */
public class UserBuddyRoute extends VertxApplication {

    private static Logger logger = Logger.getLogger(UserBuddyRoute.class);

    private static UserBuddyService userBuddyService = new UserBuddyService();

    public static void trigger() {
        searchUserBuddy("/user/buddy");
    }

    /**
     * 缓存好友信息
     * @param userId 用户ID
     * @param userBuddyVOS 用户好友列表
     */
    private static void setTeacherInfoByRedis(String userId, List<UserBuddyVO> userBuddyVOS) {
        if (!userBuddyVOS.isEmpty()) {
            String jsonBean = JSONArray.toJSONString(userBuddyVOS);
            RedisAPI redisAPI = RedisVerticle.getRedisAPI();
            redisAPI.getset(userId, jsonBean, handler -> {
                if (handler.succeeded()) {
                    System.out.println("redis insert bean succeeded");
                } else {
                    System.out.println("redis insert bean failed");
                }
            });
        }
    }

    /**
     * 通过用户ID查找所有好友
     *
     * @param routePath
     */
    private static void searchUserBuddy(String routePath) {
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                try {
                    RequestParams params = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    final UserBuddyVO userBuddy = JSONObject.parseObject(params.getParams().toString(), UserBuddyVO.class);
                    if (userBuddy != null && userBuddy.getUserId() != null && !"".equals(userBuddy.getUserId())) {
                        // 尝试从redis中获取
                        RedisAPI redisAPI = RedisVerticle.getRedisAPI();
                        redisAPI.get(userBuddy.getUserId(), redisHandler -> {
                            String result = null;
                            List<UserBuddyVO> userBuddyVOS;
                            try {
                                Response response = redisHandler.result();
                                if (redisHandler.succeeded() && response != null) {
                                    // 获取到用户好友列表
                                    userBuddyVOS = JSONArray.parseArray(result.toString(), UserBuddyVO.class);
                                } else {
                                    // 尝试从DB中获取
                                    userBuddyVOS = userBuddyService.searchUserBuddyByUserId(userBuddy);
                                }
                                if (!userBuddyVOS.isEmpty()) {
                                    setTeacherInfoByRedis(userBuddy.getUserId(), userBuddyVOS);
                                    result = new ResponseBody(ResponseCode.SUCCESS, "查询成功", userBuddyVOS).getJson();
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
                        throw new Exception("业务异常: 参数异常");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                    HttpServerResponse response = routingContext.response();
                    // 返回查询结果
                    response.putHeader("content-type", "application/json");
                    // 写入响应并结束处理
                    response.end(new ResponseBody(ResponseCode.BUSINESS_ERROR, "查询失败", null).getJson());
                }
            });
        };
        // 注册服务
        VertxApplication.addRoutePost(routePath, handler);
    }

}
