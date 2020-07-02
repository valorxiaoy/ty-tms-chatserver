package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.GuardionInfo;

import java.util.List;

public interface GuardionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GuardionInfo record);

    int insertSelective(GuardionInfo record);

    GuardionInfo selectByPrimaryKey(Integer id);

    List<GuardionInfo> findAll(GuardionInfo record);

    int updateByPrimaryKeySelective(GuardionInfo record);

    int updateByPrimaryKey(GuardionInfo record);
}