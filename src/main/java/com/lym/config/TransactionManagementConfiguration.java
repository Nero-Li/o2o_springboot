package com.lym.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 对spring-service里面的transactionManager
 * 继承TransactionManagementConfigurer是因为开启了annotation-driven
 * @ClassName TransactionManagementConfiguration
 * @Author lyming
 * @Date 2019/4/9 23:07
 **/
@Configuration
//首先使用注解@EnableTransactionManagement 开启事务支持后
//在service方法上添加注解@Transactional即可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

    @Autowired
    //注入DataSourceConfiguration里面的dataSource,通过createDataSource()获取
    private DataSource dataSource;

    @Override
    /**
     * 关于事务管理,需要返回PlatformTransactionManager的实现
     */
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
