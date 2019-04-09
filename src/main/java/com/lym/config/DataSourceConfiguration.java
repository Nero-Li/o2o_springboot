package com.lym.config;

import com.lym.util.DESUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * 配置dataSource到ioc容器
 * @ClassName DataSourceConfiguration
 * @Author lyming
 * @Date 2019/4/9 16:58
 **/
@Configuration
//相当于MapperScannerConfigurer,配置mybatis的扫描路径
@MapperScan("com.lym.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * 生成与spring-dao.xml对应的bean dataSource
     */
    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        //生成dataSource实例
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //和spring-dao.xml配置一样设置以下信息
        //驱动
        dataSource.setDriverClass(jdbcDriver);
        //数据库连接URL
        dataSource.setJdbcUrl(jdbcUrl);
        //设置账号(需要解密)
        dataSource.setUser(DESUtils.getDecryptString(jdbcUsername));
        //设置密码(需要解密)
        dataSource.setPassword(DESUtils.getDecryptString(jdbcPassword));
        //配置c3p0连接池私有属性
        //连接池最大线程数
        dataSource.setMaxPoolSize(30);
        //连接池最小线程数
        dataSource.setMinPoolSize(10);
        //关闭连接后不自动commot
        dataSource.setAutoCommitOnClose(false);
        //连接超时时间
        dataSource.setCheckoutTimeout(10000);
        //连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);

        return dataSource;
    }

}
