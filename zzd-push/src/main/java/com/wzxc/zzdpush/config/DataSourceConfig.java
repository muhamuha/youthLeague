package com.wzxc.zzdpush.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {

    // 信产数据库
    @Primary
    @Bean(name = "ds1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSourceProperties ds1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ds1DataSource")
    public DataSource ds1DataSource(@Qualifier("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("初始化数据源 - 信产");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // 城市大脑开发数据库
    @Bean(name = "ds2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    @ConditionalOnProperty(name="spring.profiles.active", havingValue="dev")
    public DataSourceProperties ds2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ds2DataSource")
    @ConditionalOnProperty(name="spring.profiles.active", havingValue="dev")
    public DataSource ds2DataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("初始化数据源 - 城市大脑开发服务器");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // 城市大脑生产数据库
    @Bean(name = "ds3DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds3")
    @ConditionalOnProperty(name="spring.profiles.active", havingValue="prod")
    public DataSourceProperties ds3DataSourceProperties()
    {
        return new DataSourceProperties();
    }

    @Bean(name = "ds3DataSource")
    @ConditionalOnProperty(name="spring.profiles.active", havingValue="prod")
    public DataSource ds3DataSource(@Qualifier("ds3DataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("初始化数据源 - 城市大脑生产服务器");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
