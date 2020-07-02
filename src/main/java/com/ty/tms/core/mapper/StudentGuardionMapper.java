package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.StudentGuardion;

public interface StudentGuardionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentGuardion record);

    int insertSelective(StudentGuardion record);

    StudentGuardion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentGuardion record);

    int updateByPrimaryKey(StudentGuardion record);
}