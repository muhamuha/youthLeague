package com.wzxc.zzdpush.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.wzxc.zzdpush.dao.ds1", sqlSessionTemplateRef = "ds1SqlSessionTemplate")
@Slf4j
public class Ds1Config {

    {
        log.info("初始化mybatis配置 - 信产");
    }

    @Bean(name = "ds1SqlSessionFactory")
    @Primary
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("ds1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/system/ds1/*.xml"));
        bean.setTypeAliasesPackage("com.wzxc.zzdpush.vo");
        return bean.getObject();
    }

    @Bean(name = "ds1TransactionManager")
    @Primary
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("ds1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate ds1SqlSessionTemplate(@Qualifier("ds1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
