package com.wzxc.webservice.config.web;

import com.wzxc.webservice.config.condition.BusiCondition;
import com.wzxc.webservice.exception.MyExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 添加MultiRequestBody参数解析器
        argumentResolvers.add(new MultiRequestBodyArgumentResolver());
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        // 解决中文乱码问题
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }
}
