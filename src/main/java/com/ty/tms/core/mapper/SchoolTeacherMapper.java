package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.SchoolTeacher;

public interface SchoolTeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolTeacher record);

    int insertSelective(SchoolTeacher record);

    SchoolTeacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolTeacher record);

    int updateByPrimaryKey(SchoolTeacher record);
}