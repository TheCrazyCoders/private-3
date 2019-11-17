package com.techsophy.vsc.bo.controllertest;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;

import com.techsophy.vsc.bo.VscBoApplication;
@ContextConfiguration(classes=VscBoApplication.class)
@Configuration
public class DBConfiguration {
 
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder dataSource = new EmbeddedDatabaseBuilder();
        dataSource.setType(EmbeddedDatabaseType.H2);
        dataSource.addScript("classpath:create-tables.sql").addScript("classpath:insert-data.sql");
        return dataSource.build();
    }
}