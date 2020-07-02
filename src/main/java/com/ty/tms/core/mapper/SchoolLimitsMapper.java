package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.SchoolLimits;

public interface SchoolLimitsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolLimits record);

    int insertSelective(SchoolLimits record);

    SchoolLimits selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolLimits record);

    int updateByPrimaryKeyWithBLOBs(SchoolLimits record);

    int updateByPrimaryKey(SchoolLimits record);
}