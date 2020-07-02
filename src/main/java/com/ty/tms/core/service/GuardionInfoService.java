package com.ty.tms.core.service;

import com.ty.tms.core.bean.po.GuardionInfo;
import com.ty.tms.core.config.mybaits.MyBatisUtil;
import com.ty.tms.core.mapper.GuardionInfoMapper;
import org.apache.log4j.Logger;

import java.util.List;

public class GuardionInfoService {

    private static Logger logger = Logger.getLogger(GuardionInfoService.class);

    private static GuardionInfoMapper guardionInfoMapper = MyBatisUtil.getMapper(GuardionInfoMapper.class);

    public List<GuardionInfo> searchGuardionInfoAll(GuardionInfo guardionInfo) {
        List<GuardionInfo> list = guardionInfoMapper.findAll(guardionInfo);
        return list;
    }

    public GuardionInfo searchGuardionInfo(Integer id) {
        GuardionInfo guardionInfo = guardionInfoMapper.selectByPrimaryKey(id);
        MyBatisUtil.clearCache();
        return guardionInfo;
    }

}
