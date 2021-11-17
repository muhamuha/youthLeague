package com.wzxc.camunda.config;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaDatasourceConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.camunda.bpm.spring.boot.starter.property.DatabaseProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@Primary
public class MyDefaultDatasourceConfiguration extends AbstractCamundaConfiguration implements CamundaDatasourceConfiguration {

    @Autowired
    @Qualifier("ds2TransactionManager")
    protected PlatformTransactionManager transactionManager;

    @Autowired(required = false)
    @Qualifier("camundaBpmTransactionManager")
    protected PlatformTransactionManager camundaTransactionManager;

    @Autowired
    @Qualifier("ds2DataSource")
    protected DataSource dataSource;

    @Autowired(required = false)
    @Qualifier("camundaBpmDataSource")
    protected DataSource camundaDataSource;

    public MyDefaultDatasourceConfiguration() {
    }

    public void preInit(SpringProcessEngineConfiguration configuration) {
        DatabaseProperty database = this.camundaBpmProperties.getDatabase();
        if (this.camundaTransactionManager == null) {
            configuration.setTransactionManager(this.transactionManager);
        } else {
            configuration.setTransactionManager(this.camundaTransactionManager);
        }

        if (this.camundaDataSource == null) {
            configuration.setDataSource(this.dataSource);
        } else {
            configuration.setDataSource(this.camundaDataSource);
        }

        configuration.setDatabaseType(database.getType());
        configuration.setDatabaseSchemaUpdate(database.getSchemaUpdate());
        if (!StringUtils.isEmpty(database.getTablePrefix())) {
            configuration.setDatabaseTablePrefix(database.getTablePrefix());
        }

        if (!StringUtils.isEmpty(database.getSchemaName())) {
            configuration.setDatabaseSchema(database.getSchemaName());
        }

        configuration.setJdbcBatchProcessing(database.isJdbcBatchProcessing());
    }

    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public PlatformTransactionManager getCamundaTransactionManager() {
        return this.camundaTransactionManager;
    }

    public void setCamundaTransactionManager(PlatformTransactionManager camundaTransactionManager) {
        this.camundaTransactionManager = camundaTransactionManager;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getCamundaDataSource() {
        return this.camundaDataSource;
    }

    public void setCamundaDataSource(DataSource camundaDataSource) {
        this.camundaDataSource = camundaDataSource;
    }
}
