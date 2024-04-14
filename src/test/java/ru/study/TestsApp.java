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
import ru.study.inspects.*;
import ru.study.repository.LoginsRepository;
import ru.study.servises.ReadDataFile;
import ru.study.servises.WriteDataBase;
import ru.study.servises.WriteDataFile;
import ru.study.utils.CheckDataProxy;

import java.util.ArrayList;
import java.util.List;

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
    LoginsRepository loginsRepository;
    @Autowired
    WriteDataBase writeDataBase;
    @Autowired
    @Qualifier("file_source")
    ReadDataFile readDataFile;
    @Autowired
    WriteDataFile writeDataFile;
    @Test
    public void tst() throws InterruptedException {
        List<CheckData> inspects = new ArrayList<>();

        CheckData checkDataFIO = new CheckDataFIO();
        CheckDataProxy checkDataProxy = new CheckDataProxy<CheckData>(checkDataFIO,writeDataFile);
        inspects.add((CheckData) checkDataProxy.proxy(checkDataFIO,writeDataFile));

        CheckData checkDataApp = new CheckDataApp();
        checkDataProxy = new CheckDataProxy<CheckData>(checkDataApp,writeDataFile);
        inspects.add((CheckData) checkDataProxy.proxy(checkDataApp,writeDataFile));

        CheckData checkDataDate = new CheckDataDate();
        checkDataProxy = new CheckDataProxy<CheckData>(checkDataDate,writeDataFile);
        inspects.add((CheckData) checkDataProxy.proxy(checkDataDate,writeDataFile));

        for (String s : readDataFile.read()) {

            String arrData[] = s.split(";");
            writeDataBase.write(arrData[0].trim(),arrData[1].trim(),arrData[2].trim(),arrData[3].trim(),inspects,writeDataFile,readDataFile.getFileName());

        }

        //checkLowerCase
        Assert.assertNotEquals("max maximov Maximovich", checkDataFIO.check("max maximov Maximovich"));
        //checkApplicationName
        Assert.assertNotEquals("app", checkDataApp.check("app"));
        //checkDate exist
        Assert.assertEquals("null", checkDataDate.check(""));

        System.out.println(loginsRepository.findById(1));
        System.out.println(loginsRepository.findById(2));
        System.out.println(loginsRepository.findById(3));
        System.out.println(loginsRepository.findById(4));
        System.out.println(loginsRepository.findById(5));

    }

}
