package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.SchoolStudent;

public interface SchoolStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolStudent record);

    int insertSelective(SchoolStudent record);

    SchoolStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolStudent record);

    int updateByPrimaryKey(SchoolStudent record);
}