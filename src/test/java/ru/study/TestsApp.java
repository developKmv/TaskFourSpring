package ru.study;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.study.entity.Logins;
import ru.study.repository.LoginsRepository;
import ru.study.repository.UserRepository;
import ru.study.entity.User;
import ru.study.servises.ReadData;
import ru.study.servises.ReadDataFile;
import ru.study.utils.MyUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest()
public class TestsApp {

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        log.info("url: " + postgres.getJdbcUrl());
        registry.add("spring.datasource.username", postgres::getUsername);
        log.info("userName: " + postgres.getUsername());
        registry.add("spring.datasource.password", postgres::getPassword);
        log.info("password: " + postgres.getPassword());
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginsRepository loginsRepository;
    @Autowired
    @Qualifier("file_source")
    ReadData readDataFile;

    @Test
    public void tst() throws InterruptedException {
        for (String s : readDataFile.read()) {
            List<Object> result = new ArrayList<>();
            String arrData[] = s.split(";");

            User user = new User(null, arrData[0], arrData[1]);
            userRepository.save(user);

            Logins logins = new Logins(null, new Timestamp(System.currentTimeMillis()),arrData[2].substring(0, MyUtils.extraCharacterIndex(arrData[2])), user);
            loginsRepository.save(logins);
        }

        System.out.println(loginsRepository.findById(1));

    }

}
