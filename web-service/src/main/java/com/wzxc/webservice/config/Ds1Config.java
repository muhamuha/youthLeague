package com.wzxc.webservice.config;

import com.wzxc.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan(basePackages = "com.wzxc.busi.dao.ds1", sqlSessionTemplateRef = "ds1SqlSessionTemplate")
@Slf4j
public class Ds1Config {

    @Bean(name = "ds1SqlSessionFactory")
    @Primary
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("ds1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        String activeProfile = SpringUtils.getActiveProfile(); // 判断环境
        String path = "";
        List<Resource> resources = new ArrayList<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        switch (activeProfile){
            case "test" : case "dev": { path = "classpath:mapper/system/ds1/test/*.xml"; break; }
            case "prod": { path = "classpath:mapper/system/ds1/prod/*.xml"; break; }
            default: { path = "classpath:mapper/system/ds1/test/*.xml"; break; }
        }
        try{
            Resource[] rs = pathMatchingResourcePatternResolver.getResources(path);
            for(Resource r : rs){
                resources.add(r);
            }
        } catch(FileNotFoundException e){
            log.warn(activeProfile + " ---- 没有找到mapper.xml文件！！！路径 ---- " + path);
        }
        // 加载通用mapper.xml
        path = "classpath:mapper/system/ds1/common/*.xml";
        try{
            Resource[] rs = pathMatchingResourcePatternResolver.getResources(path);
            for(Resource r1 : rs){
                boolean isExist = false;
                for(Resource r2 : resources){
                    if(r1.getFilename().equals(r2.getFilename())){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){ // 如果通用mapper路径下存在没有的mapper文件则添加进去
                    resources.add(r1);
                }
            }
        } catch(FileNotFoundException e){
            log.error(activeProfile + " ---- 没有找到mapper.xml文件！！！路径 ---- " + path);
        }
        for(Resource r : resources){
            log.info(r.getURI().toString());
        }
        bean.setMapperLocations(resources.toArray(new Resource[resources.size()]));
        bean.setTypeAliasesPackage("com.wzxc.busi.vo");
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
