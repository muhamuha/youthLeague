package com.wzxc.webservice.config.Camunda;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.wzxc.webservice.config.condition.CamundaCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Conditional({CamundaCondition.class})
@Configuration
@MapperScan(basePackages = "com.wzxc.camunda.dao", sqlSessionTemplateRef = "ds2SqlSessionTemplate")
@Slf4j
public class Ds2Config {

    @Bean(name = "ds2SqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("ds2DataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        String path = "classpath:mapper/camunda/*.xml";
        List<Resource> resources = new ArrayList<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        try{
            Resource[] rs = pathMatchingResourcePatternResolver.getResources(path);
            for(Resource r : rs){
                resources.add(r);
                log.info(r.getURI().toString());
            }
            bean.setMapperLocations(resources.toArray(new Resource[resources.size()]));
            bean.setTypeAliasesPackage("com.wzxc.camunda.persistence.entity");
        } catch (FileNotFoundException e){
            log.warn("mybatis --- 未找到xml映射文件，地址：" + path);
        }
        return bean.getObject();
    }

    @Bean(name = "ds2TransactionManager")
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("ds2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds2SqlSessionTemplate")
    public SqlSessionTemplate ds1SqlSessionTemplate(@Qualifier("ds2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
