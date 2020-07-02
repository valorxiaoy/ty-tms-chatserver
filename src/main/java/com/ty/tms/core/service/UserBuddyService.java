package com.ty.tms.core.service;

import com.alibaba.fastjson.JSONArray;
import com.ty.tms.core.bean.po.UserBuddy;
import com.ty.tms.core.bean.vo.UserBuddyVO;
import com.ty.tms.core.config.mybaits.MyBatisUtil;
import com.ty.tms.core.mapper.UserBuddyMapper;
import org.apache.log4j.Logger;

import java.util.List;

public class UserBuddyService {

    private static Logger logger = Logger.getLogger(UserBuddyService.class);

    private static UserBuddyMapper userBuddyMapper = MyBatisUtil.getMapper(UserBuddyMapper.class);

    /**
     * 通过用户ID获取用户好友列表
     * @param userBuddyVO
     * @return
     */
    public List<UserBuddyVO> searchUserBuddyByUserId(UserBuddyVO userBuddyVO) {
        // 封装查询对象
        UserBuddy userBuddy  = new UserBuddy();
        userBuddy.setUserId(Integer.parseInt(userBuddyVO.getUserId()));
        List<UserBuddy> userBuddies = userBuddyMapper.searchUserBuddyByUserId(userBuddy);
        String json = JSONArray.toJSONString(userBuddies);
        // 将PO对象转换成VO对象
        List<UserBuddyVO> userBuddyVOS = JSONArray.parseArray(json, UserBuddyVO.class);
        return userBuddyVOS;
    }
}
