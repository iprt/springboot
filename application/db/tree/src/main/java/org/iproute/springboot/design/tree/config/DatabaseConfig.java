package org.iproute.springboot.design.tree.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * DatabaseConfig
 * <p>
 * 1. datasource配置
 * 2. liquibase配置
 * 3. reference: <a href="https://www.jianshu.com/p/1d42731dc28b">...</a>
 *
 * @author zhuzhenjie
 * @since 2023/1/6
 */
@Configuration
public class DatabaseConfig {

    @Resource
    private DataSource dataSource;


    @Bean("mysqlLiquibaseProperties")
    @ConfigurationProperties(prefix = "spring.liquibase.mysql")
    public LiquibaseProperties mysqlLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean("postgresLiquibaseProperties")
    @ConfigurationProperties(prefix = "spring.liquibase.postgres")
    public LiquibaseProperties postgresLiquibaseProperties() {
        return new LiquibaseProperties();
    }


    /**
     * Mysql liquibase spring liquibase.
     *
     * @param properties the properties
     * @return the spring liquibase
     */
    @Bean("mysqlLiquibase")
    public SpringLiquibase mysqlLiquibase(@Qualifier("mysqlLiquibaseProperties") LiquibaseProperties properties) {
        return createSpringLiquibaseBean(((DynamicRoutingDataSource) dataSource), "mysql", properties);
    }

    /**
     * Postgres liquibase spring liquibase.
     *
     * @param properties the properties
     * @return the spring liquibase
     */
    @Bean("postgresLiquibase")
    public SpringLiquibase postgresLiquibase(@Qualifier("postgresLiquibaseProperties") LiquibaseProperties properties) {
        return createSpringLiquibaseBean(((DynamicRoutingDataSource) dataSource), "postgres", properties);
    }

    /**
     * Create spring liquibase bean spring liquibase.
     *
     * @param dynamicRoutingDataSource the dynamic routing data source
     * @param ds                       the ds
     * @param properties               the properties
     * @return the spring liquibase
     */
    private static SpringLiquibase createSpringLiquibaseBean(DynamicRoutingDataSource dynamicRoutingDataSource,
                                                             String ds,
                                                             LiquibaseProperties properties) {
        DataSource dataSource = dynamicRoutingDataSource.getDataSource(ds);

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }

}
