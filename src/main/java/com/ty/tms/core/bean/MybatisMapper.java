package com.ty.tms.core.bean;

import org.apache.ibatis.session.SqlSession;

public class MybatisMapper {

    private Object mapper;

    private SqlSession sqlSession;

    public Object getMapper() {
        return mapper;
    }

    public void setMapper(Object mapper) {
        this.mapper = mapper;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
