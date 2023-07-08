package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.Properties;


public class Config {


    @Bean
    EntityManagerFactory entityManagerFactoryCustom() {
        return Persistence.createEntityManagerFactory("tutorial");
    }



}
