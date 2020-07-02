package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.UserCertinfo;

public interface UserCertinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCertinfo record);

    int insertSelective(UserCertinfo record);

    UserCertinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCertinfo record);

    int updateByPrimaryKey(UserCertinfo record);
}