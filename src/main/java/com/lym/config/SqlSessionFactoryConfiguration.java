package com.lym.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @ClassName SqlSessionFactoryConfiguration
 * @Author lyming
 * @Date 2019/4/9 17:15
 **/
@Configuration
public class SqlSessionFactoryConfiguration {
    //mybatis-config.xml配置文件路径

    private static String mybatisConfigFile;
    /**
     * 需要去掉static,不然不能读到值
     * @param mybatisConfigFile
     */
    @Value("${mybatis_config_file}")
    public void setMybatisConfigFile(String mybatisConfigFile) {
        SqlSessionFactoryConfiguration.mybatisConfigFile = mybatisConfigFile;
    }

    //mybatis mapper 文件所在路径
    private static String mapperPath;
    @Value("${mapper_path}")
    public void setMapperPath(String mapperPath) {
        SqlSessionFactoryConfiguration.mapperPath = mapperPath;
    }

    //实体类所在的package
    @Value("${type_alias_package}")
    private String typeAliasPackage;

    @Autowired
    private DataSource dataSource;

    /**
     * 创建SqlSessionFactoryBean实例,并设置configuration 设置mapepr映射路径
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置mybatis configuration扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
        //添加mapper 扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        //设置dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        //设置typeAlias 包扫描路径
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);

        return sqlSessionFactoryBean;
    }
}
