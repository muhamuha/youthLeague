package com.wzxc.zzdpush.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.wzxc.zzdpush.dao.ds2", sqlSessionTemplateRef = "ds3SqlSessionTemplate")
@ConditionalOnProperty(name="spring.profiles.active", havingValue="prod")
@Slf4j
public class Ds3Config {

    {
        log.info("初始化mybatis配置 - 城市大脑生产服务器");
    }

    @Bean(name = "ds3SqlSessionFactory")
    @Primary
    public SqlSessionFactory ds3SqlSessionFactory(@Qualifier("ds3DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/system/ds2/*.xml"));
        bean.setTypeAliasesPackage("com.wzxc.zzdpush.vo");
        return bean.getObject();
    }

    @Bean(name = "ds3TransactionManager")
    @Primary
    public DataSourceTransactionManager ds3TransactionManager(@Qualifier("ds3DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds3SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate ds3SqlSessionTemplate(@Qualifier("ds3SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
