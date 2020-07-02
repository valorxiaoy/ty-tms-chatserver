package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.Limits;

public interface LimitsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Limits record);

    int insertSelective(Limits record);

    Limits selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Limits record);

    int updateByPrimaryKey(Limits record);
}