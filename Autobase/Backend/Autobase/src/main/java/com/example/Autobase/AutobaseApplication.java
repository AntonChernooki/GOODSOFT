package com.example.Autobase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/*8.Система Автобаза.Диспетчер распределяет заявки на Рейсы между Водителями и назначает для этого Автомобиль.
Водитель может сделать заявку на ремонт.Диспетчер может отстранить Водителя от работы.
Водитель делает отметку о выполнении Рейса и состоянии Автомобиля */

@SpringBootApplication
@MapperScan("com.example.Autobase.dao")

public class AutobaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutobaseApplication.class, args);
    }

}
