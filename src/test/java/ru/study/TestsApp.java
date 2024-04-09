package ru.study;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
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
import ru.study.inspects.CheckData;
import ru.study.inspects.CheckDataImpl;
import ru.study.repository.LoginsRepository;
import ru.study.repository.UserRepository;
import ru.study.entity.User;
import ru.study.servises.ReadDataFile;
import ru.study.servises.WriteDataFile;
import ru.study.utils.CheckDataProxy;
import ru.study.utils.MyUtils;

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
    ReadDataFile readDataFile;
    @Autowired
    WriteDataFile writeDataFile;

    @Autowired
    @Qualifier("checkDataImp")
    CheckData checkData;
    @Autowired
    CheckDataProxy checkDataProxy;
    @Test
    public void tst() throws InterruptedException {
        CheckData proxy = (CheckData) checkDataProxy.proxy(checkData,writeDataFile);

        for (String s : readDataFile.read()) {

            String arrData[] = s.split(";");
            User user = new User(null, arrData[0], proxy.checkAndChangeUpper(arrData[1]));

            userRepository.save(user);
            //checkDate exist
            if (proxy.checkExistDate(arrData[2], s, readDataFile.getFileName(), writeDataFile) == true) {
                userRepository.save(user);
                Logins logins = new Logins(null, proxy.createDate(arrData[2]),
                        proxy.checkApplicationName(arrData[3].substring(0, MyUtils.extraCharacterIndex(arrData[3]))),
                        user);
                loginsRepository.save(logins);
            }


        }

        //checkLowerCase
        Assert.assertNotEquals("max maximov Maximovich", userRepository.findById(3).get().getFio());
        //checkApplicationName
        Assert.assertNotEquals("app", loginsRepository.findById(4).get().getApplication());
        //checkDate exist
        Assert.assertEquals("", readDataFile.read()[3].split(";")[2]);

        System.out.println(loginsRepository.findById(1));
        System.out.println(loginsRepository.findById(2));
        System.out.println(loginsRepository.findById(3));
        System.out.println(loginsRepository.findById(4));
        System.out.println(loginsRepository.findById(5));

    }

}
