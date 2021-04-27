package shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public MyShiroRealm shiroRealm() {
        // 配置 Realm
        return new MyShiroRealm();
    }

    // 创建DefaultWebSecurityManager
    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    // 创建ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加Shiro过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */

        // 在 Shiro过滤器链上加入 自定义过滤器JWTFilter 并取名为jwt
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 自定义url规则
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 放行swagger和druid的静态资源
        filterChainDefinitionMap.put("/doc.html","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/swagger/**","anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/**","anon");
        filterChainDefinitionMap.put("/druid/**","anon");
        // 其他所有请求都要经过 jwt过滤器
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        // 设置代理类
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);

        return creator;
    }

    /**
     * 开启aop注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    // Shiro生命周期处理器
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
