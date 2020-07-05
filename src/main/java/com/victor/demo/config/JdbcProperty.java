package com.victor.demo.config;

import com.alibaba.fastjson.JSON;
import org.springframework.util.Assert;

public class JdbcProperty {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String autoCommitOnClose;
    private String minPoolSize;
    private String maxPoolSize;
    private String initialPoolSize;
    private String maxIdleTime;
    private String acquireIncrement;
    private String checkoutTimeout;
    private String maxStatements;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAutoCommitOnClose() {
        return autoCommitOnClose;
    }

    public void setAutoCommitOnClose(String autoCommitOnClose) {
        this.autoCommitOnClose = autoCommitOnClose;
    }

    public String getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(String minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public String getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(String maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(String initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public String getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(String maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public String getAcquireIncrement() {
        return acquireIncrement;
    }

    public void setAcquireIncrement(String acquireIncrement) {
        this.acquireIncrement = acquireIncrement;
    }

    public String getCheckoutTimeout() {
        return checkoutTimeout;
    }

    public void setCheckoutTimeout(String checkoutTimeout) {
        this.checkoutTimeout = checkoutTimeout;
    }

    public String getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(String maxStatements) {
        this.maxStatements = maxStatements;
    }

    public static JdbcProperty getJdbcProperty() {
        String jdbcProperty = System.getenv("JDBC_PROPERTY");
        Assert.notNull(jdbcProperty, "环境变量JDBC_PROPERTY尚未配置");
        try {
            return JSON.parseObject(jdbcProperty, JdbcProperty.class);
        } catch (Exception e){
            throw new RuntimeException(String.format("环境变量数据库配置JDBC_PROPERTY， %s格式错误",  jdbcProperty), e);
        }
    }
}
