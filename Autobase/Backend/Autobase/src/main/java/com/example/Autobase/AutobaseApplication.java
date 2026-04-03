package com.example.Autobase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.Autobase.dao")

public class AutobaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutobaseApplication.class, args);
    }

}
