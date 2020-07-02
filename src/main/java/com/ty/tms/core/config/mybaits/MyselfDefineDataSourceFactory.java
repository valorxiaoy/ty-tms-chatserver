package com.ty.tms.core.config.mybaits;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyselfDefineDataSourceFactory extends UnpooledDataSourceFactory {

    public MyselfDefineDataSourceFactory() throws IOException {

        String CONFIG_PATH = "config/dbConfig.properties";
        InputStream stream = Resources.getResourceAsStream(CONFIG_PATH);
        Properties properties = new Properties();
        properties.load(stream);
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        pooledDataSourceFactory.setProperties(properties);
        DataSource dataSource = pooledDataSourceFactory.getDataSource();
        this.dataSource = dataSource;
    }
}
