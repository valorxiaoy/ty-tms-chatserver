package com.ty.tms.core.service;

import com.ty.tms.core.bean.po.SchoolInfo;
import com.ty.tms.core.config.mybaits.MyBatisUtil;
import com.ty.tms.core.mapper.SchoolInfoMapper;
import org.apache.log4j.Logger;

import java.util.List;

public class SchoolInfoService {

    private static Logger logger = Logger.getLogger(SchoolInfoService.class);

    private static SchoolInfoMapper schoolInfoMapper = MyBatisUtil.getMapper(SchoolInfoMapper.class);

    public List<SchoolInfo> searchSchoolInfoAll(SchoolInfo schoolInfo) {
        List<SchoolInfo> list = schoolInfoMapper.findAll(schoolInfo);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("查询结果 ： ");
        stringBuffer.append(list.size());
        logger.info(stringBuffer.toString());
        return list;
    }

    public SchoolInfo searchSchoolInfo(Integer id) {
        SchoolInfo schoolInfo = schoolInfoMapper.selectByPrimaryKey(id);
        // MyBatisUtil.clearCache();
        return schoolInfo;
    }
}
