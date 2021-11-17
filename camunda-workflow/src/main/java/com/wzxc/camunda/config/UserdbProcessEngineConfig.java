package com.wzxc.camunda.config;

import com.wzxc.camunda.identity.plugin.JpaIdentityProviderPlugin;
import com.wzxc.camunda.identity.query.UserIdentityQuery;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.spring.boot.starter.configuration.impl.DefaultDatasourceConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserdbProcessEngineConfig {

    @Value("${spring.datasource.ds2.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.ds2.url}")
    private String dataUrl;
    @Value("${spring.datasource.ds2.username}")
    private String username;
    @Value("${spring.datasource.ds2.password}")
    private String password;

    @Bean
    public ProcessEnginePlugin administratorAuthorizationPlugin() {
        AdministratorAuthorizationPlugin administratorAuthorizationPlugin = new AdministratorAuthorizationPlugin();
        administratorAuthorizationPlugin.setAdministratorGroupName("admins");
        return administratorAuthorizationPlugin;
    }

    @Bean
    public ProcessEnginePlugin identityProviderPlugin(UserIdentityQuery userIdentityService) {
        return new JpaIdentityProviderPlugin(userIdentityService);
    }

//    @Bean
//    @Primary
//    public ProcessEngine processEngineConfiguration() {
//        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//
//        processEngineConfiguration.setJdbcDriver(driver);
//        processEngineConfiguration.setJdbcUrl(dataUrl);
//        processEngineConfiguration.setJdbcUsername(username);
//        processEngineConfiguration.setJdbcPassword(password);
//
//        processEngineConfiguration.setHistoryLevel(HistoryLevel.HISTORY_LEVEL_FULL);
//        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
//        return processEngine;
//    }

}
