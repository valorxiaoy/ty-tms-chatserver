package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.FriendRequests;

public interface FriendRequestsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FriendRequests record);

    int insertSelective(FriendRequests record);

    FriendRequests selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FriendRequests record);

    int updateByPrimaryKey(FriendRequests record);
}