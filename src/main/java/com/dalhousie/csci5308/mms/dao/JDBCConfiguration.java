package com.dalhousie.csci5308.mms.dao;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class JDBCConfiguration {
    private static DataSource dataSource;
    private static JDBCConfiguration instance;
    public static JDBCConfiguration getInstance() // singleton pattern so that only one variable holds the gamestate
    {
        if(instance == null){
            instance = new JDBCConfiguration();
        }
        return instance;
    }
    @Bean
     public DataSource getDataSource() throws IOException {

        if(JDBCConfiguration.dataSource == null) {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            PropertiesLoader loader = new PropertiesLoader();
            Properties applicationProperties=loader.loadProperties();
            dataSourceBuilder.driverClassName(applicationProperties.getProperty("spring.datasource.driver-class-name"));
            dataSourceBuilder.url(applicationProperties.getProperty("spring.datasource.url"));
            dataSourceBuilder.username(applicationProperties.getProperty("spring.datasource.username"));
            dataSourceBuilder.password(applicationProperties.getProperty("spring.datasource.password"));
            JDBCConfiguration.dataSource=dataSourceBuilder.build();
        }
       return JDBCConfiguration.dataSource;
    }
}
