package com.example.backresultados;

import com.example.backresultados.config.CassandraConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRabbit
@SpringBootApplication
public class BackResultadosApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackResultadosApplication.class, args);
    }

}
