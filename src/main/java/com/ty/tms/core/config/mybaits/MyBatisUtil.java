package com.ty.tms.core.config.mybaits;

import com.ty.tms.core.bean.MybatisMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisUtil {
    /*
     * 定义配置文件的位置
     */
    private static final String CONFIG_PATH = "config/mybatis-config.xml";

    private static SqlSession sqlSession = null;

    private static void init() {

        try {
            if (sqlSession == null) {
                InputStream stream = Resources.getResourceAsStream(CONFIG_PATH);
                // 可以根据配置的相应环境读取相应的数据库环境
                // SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream, "development");
                SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
                sqlSession = factory.openSession();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getMapper(Class<T> cls) {
        init();
        T mapper = sqlSession.getMapper(cls);
        return mapper;
    }

    public static void commit(boolean isCommit) {
        sqlSession.commit(isCommit);
    }

    public static void rollback() {
        sqlSession.rollback();
    }

    public static void closeSession() {
        sqlSession.close();
    }

    public static void clearCache() {
        sqlSession.clearCache();
    }
}
