package com.victor.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Slf4j
@Configuration
public class MainConfiguration {
    @Bean
    @QuartzDataSource
    public DataSource createDataSource(){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        JdbcProperty jdbcProperty = JdbcProperty.getJdbcProperty();
        try {
            ds.setDriverClass(jdbcProperty.getDriver());
            ds.setJdbcUrl(jdbcProperty.getUrl());
            ds.setUser(jdbcProperty.getUsername());
            ds.setPassword(jdbcProperty.getPassword());
            ds.setInitialPoolSize(Integer.parseInt(jdbcProperty.getInitialPoolSize()));
            ds.setMaxIdleTime(Integer.parseInt(jdbcProperty.getMaxIdleTime()));
            ds.setMinPoolSize(Integer.parseInt(jdbcProperty.getMinPoolSize()));
            ds.setMaxPoolSize(Integer.parseInt(jdbcProperty.getMaxPoolSize()));
            ds.setAcquireIncrement(Integer.parseInt(jdbcProperty.getAcquireIncrement()));
            ds.setMaxStatements(Integer.parseInt(jdbcProperty.getMaxStatements()));
            log.info("加载数据库链接环境变量完毕,driver:{},Url:{}",jdbcProperty.getDriver(),jdbcProperty.getUrl());

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }
}
