package ru.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication(scanBasePackages = "ru.study")
public class Main {
    public static void main(String[] args) {
     ApplicationContext ctx = SpringApplication.run(Main.class,args);
    }
}
