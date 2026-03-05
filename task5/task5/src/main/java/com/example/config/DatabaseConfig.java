package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;


public class DatabaseConfig {

private static final DataSource dataSource;
static {
    HikariConfig config=new HikariConfig();
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/task5");
    config.setPassword("12344321");
    config.setUsername("postgres");


    dataSource=new HikariDataSource(config);
}
public static DataSource getDataSource(){
    return dataSource;
}


}
