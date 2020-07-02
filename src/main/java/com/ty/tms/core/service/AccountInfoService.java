package com.ty.tms.core.service;

import com.ty.tms.core.bean.po.AccountInfo;
import com.ty.tms.core.config.mybaits.MyBatisUtil;
import com.ty.tms.core.mapper.AccountInfoMapper;
import org.apache.log4j.Logger;

import java.util.List;

public class AccountInfoService {

    private static Logger logger = Logger.getLogger(AccountInfoService.class);

    private static AccountInfoMapper accountInfoMapper = MyBatisUtil.getMapper(AccountInfoMapper.class);

    public AccountInfo searchAccountInfo(Integer id) {
        AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(id);
        MyBatisUtil.clearCache();
        return accountInfo;
    }

    public List<AccountInfo> searchAccountInfoAll(AccountInfo accountInfo) {
        List<AccountInfo> list = accountInfoMapper.findAll(accountInfo);
        MyBatisUtil.clearCache();
        return list;
    }
}
