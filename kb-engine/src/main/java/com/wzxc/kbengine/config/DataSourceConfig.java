package com.wzxc.kbengine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {

    // 本地数据库
    @Primary
    @Bean(name = "ds1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSourceProperties ds1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ds1DataSource")
    public DataSource ds1DataSource(@Qualifier("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("初始化ds1 - 数据源");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
