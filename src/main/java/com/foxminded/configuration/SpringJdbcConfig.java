package com.foxminded.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.foxminded.dao")
@PropertySource("classpath:app.properties")
public class SpringJdbcConfig {
    @Value("${spring.datasource.jndi-name}")
    private String jndiDataSource;
    @Bean
    public DataSource postgresDataSource(){
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource(jndiDataSource);
    }
}
