package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.SchoolInfo;

import java.util.List;

public interface SchoolInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolInfo record);

    int insertSelective(SchoolInfo record);

    SchoolInfo selectByPrimaryKey(Integer id);

    List<SchoolInfo> findAll(SchoolInfo record);

    int updateByPrimaryKeySelective(SchoolInfo record);

    int updateByPrimaryKey(SchoolInfo record);
}