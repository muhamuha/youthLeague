package com.wzxc.webservice.config.Busi;

import com.wzxc.webservice.config.condition.BusiCondition;
import com.wzxc.webservice.exception.MyExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Conditional({BusiCondition.class})
@Configuration
public class WebConfig {

    /**
     * 注册filter
     */
    @Bean
    public FilterRegistrationBean<MyExceptionFilter> MyExceptionFilter() {
        FilterRegistrationBean<MyExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        MyExceptionFilter myFilter = new MyExceptionFilter();
        registrationBean.setFilter(myFilter);
        registrationBean.setName("MyExceptionFilter");
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/*"); // 配置过滤规则
        registrationBean.setUrlPatterns(urls);
        registrationBean.setOrder(-1);
        return registrationBean;
    }
}
