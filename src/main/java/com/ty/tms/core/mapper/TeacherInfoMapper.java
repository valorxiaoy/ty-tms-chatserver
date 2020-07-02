package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.TeacherInfo;

import java.util.List;

public interface TeacherInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    TeacherInfo selectByPrimaryKey(Integer id);

    List<TeacherInfo> findAll(TeacherInfo record);

    int updateByPrimaryKeySelective(TeacherInfo record);

    int updateByPrimaryKey(TeacherInfo record);
}