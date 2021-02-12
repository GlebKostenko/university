package com.foxminded.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.foxminded.dao")
public class SpringHibernateConfig {
    @Bean
    public SessionFactory buildSessionFactory(){
        try {
            return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        } catch (Throwable ex){
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
}
