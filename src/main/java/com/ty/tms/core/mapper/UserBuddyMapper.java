package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.UserBuddy;

import java.util.List;

public interface UserBuddyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBuddy record);

    int insertSelective(UserBuddy record);

    UserBuddy selectByPrimaryKey(Integer id);

    List<UserBuddy> searchUserBuddyByUserId(UserBuddy record);

    int updateByPrimaryKeySelective(UserBuddy record);

    int updateByPrimaryKey(UserBuddy record);
}