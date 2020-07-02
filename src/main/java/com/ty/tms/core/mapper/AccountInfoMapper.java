package com.ty.tms.core.mapper;

import com.ty.tms.core.bean.po.AccountInfo;

import java.util.List;

public interface AccountInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Integer id);

    List<AccountInfo> findAll(AccountInfo record);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);
}