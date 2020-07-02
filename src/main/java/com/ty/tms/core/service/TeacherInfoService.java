package com.ty.tms.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ty.tms.core.bean.po.TeacherInfo;
import com.ty.tms.core.config.mybaits.MyBatisUtil;
import com.ty.tms.core.mapper.TeacherInfoMapper;
import org.apache.log4j.Logger;

import java.util.List;

public class TeacherInfoService {

    private static Logger logger = Logger.getLogger(TeacherInfoService.class);

    private static TeacherInfoMapper teacherInfoMapper = MyBatisUtil.getMapper(TeacherInfoMapper.class);

    public TeacherInfo searchTeacherInfo(Integer id) {
        TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(id);
        return teacherInfo;
    }

    public List<TeacherInfo> searchTeacherInfoAll(TeacherInfo teacherInfo) {
        List<TeacherInfo> page = teacherInfoMapper.findAll(teacherInfo);
        return page;
    }

    public Page<TeacherInfo> searchTeacherInfoAllByPageHelper(TeacherInfo teacherInfo) {
        Page<TeacherInfo> page = PageHelper.startPage(1, 10).doSelectPage(()-> teacherInfoMapper.findAll(teacherInfo));
        return page;
    }
}
