package com.wzxc.szwenzhou.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.FileNotFoundException;

@Configuration
@MapperScan(basePackages = "com.wzxc.szwenzhou.dao.ds1", sqlSessionTemplateRef = "ds1SqlSessionTemplate")
@Slf4j
public class Ds1Config {

    {
        log.info("初始化ds1 - mybatis配置");
    }

    @Bean(name = "ds1SqlSessionFactory")
    @Primary
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("ds1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        try{
            bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mapper/system/ds1/*.xml"));
            bean.setTypeAliasesPackage("com.wzxc.szwenzhou.vo");
            return bean.getObject();
        } catch (FileNotFoundException e){
            log.error("mybatis配置初始化失败！原因：找不到xml映射文件");
            return bean.getObject();
        }
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
