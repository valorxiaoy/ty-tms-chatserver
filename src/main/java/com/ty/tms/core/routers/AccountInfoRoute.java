package com.ty.tms.core.routers;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ty.tms.core.bean.RequestParams;
import com.ty.tms.core.bean.ResponseBody;
import com.ty.tms.core.bean.po.AccountInfo;
import com.ty.tms.core.service.AccountInfoService;
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

public class AccountInfoRoute extends VertxApplication {

    private static Logger logger = Logger.getLogger(AccountInfoRoute.class);

    private static AccountInfoService accountInfoService = new AccountInfoService();

    public static void trigger() {
        // 查询账号信息
        searchAccountInfo("/account");
        // 查询所有账号信息
        // searchAccountInfoAll("/accounts");
    }

    /**
     * 缓存账户信息
     *
     * @param accountInfo
     */
    private static void setAccountInfoByRedis(AccountInfo accountInfo) {
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
     * 查询账号信息
     *
     * @param routePath
     */
    private static void searchAccountInfo(String routePath) {
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                try {
                    RequestParams params = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    final AccountInfo accountInfo = JSONObject.parseObject(params.getParams().toString(), AccountInfo.class);
                    RedisAPI redisAPI = RedisVerticle.getRedisAPI();

                    if (accountInfo != null && accountInfo.getId() != null && !"".equals(accountInfo.getId().toString())) {
                        redisAPI.get(accountInfo.getId().toString(), redisHandler -> {
                            String result = null;
                            try {
                                AccountInfo info;
                                Response response = redisHandler.result();
                                if (redisHandler.succeeded() && response != null) {
                                    info = JSONObject.parseObject(response.toString(), AccountInfo.class);
                                } else {
                                    // 否则在数据库中查询
                                    List<AccountInfo> accountInfos = accountInfoService.searchAccountInfoAll(accountInfo);
                                    info = accountInfos.size() > 0 ? accountInfos.get(0) : null;
                                }
                                if (info != null) {
                                    setAccountInfoByRedis(info);
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

    @Deprecated
    private static void searchAccountInfoAll(String routePath) {
        Handler<RoutingContext> handler = routingContext -> {
            HttpServerRequest request = routingContext.request();
            request.handler(requestHandler -> {
                String result = null;
                try {
                    RequestParams params = JSONObject.parseObject(requestHandler.toString(), RequestParams.class);
                    AccountInfo accountInfo = JSONObject.parseObject(params.getParams().toString(), AccountInfo.class);

                    PageHelper.startPage(params.getPageNum(), params.getPageSize());
                    List<AccountInfo> list = accountInfoService.searchAccountInfoAll(accountInfo);
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
        VertxApplication.addRoutePost(routePath, handler);
    }
}
