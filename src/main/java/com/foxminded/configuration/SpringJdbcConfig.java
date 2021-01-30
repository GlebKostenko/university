package com.foxminded.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.foxminded.dao")
@PropertySource("classpath:app.properties")
public class SpringJdbcConfig {
    @Value("${url}")
    private String url;
    @Value("root")
    private String user;
    @Value("${password}")
    private String password;
    @Value("${driver}")
    private String driver;
    @Bean
    public DataSource postgresDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
