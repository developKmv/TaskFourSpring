package ru.study.servises;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.study.entity.Logins;
import ru.study.entity.User;
import ru.study.inspects.CheckData;
import ru.study.utils.MyUtils;

import java.sql.Timestamp;
import java.util.List;

@Service
@Data
public class WriteDataBase {
    @PersistenceContext
    EntityManager entityManager;

    private String address;
    private String user;
    private String password;

    public WriteDataBase(@Value("${spring.datasource.url}") String address, @Value("${spring.datasource.username}") String user
            , @Value("${spring.datasource.password}") String password) {
        this.address = address;
        this.user = user;
        this.password = password;
    }

    @Transactional
    public void write(String username, String fio, String accessDate, String application, List<CheckData> inspects, WriteDataFile writeDataFile, String fileName) {
        String checkFIO = inspects.get(0).check(fio);
        User user = new User(null, username, checkFIO);

        String checkApp = inspects.get(1).check(application);
        String checkDate = inspects.get(2).check(accessDate);
        if (!checkDate.equals("null")) {
            Logins logins = new Logins(null, MyUtils.createDate(checkDate), MyUtils.extraCharacterIndex(checkApp), user);
            entityManager.persist(user);
            entityManager.persist(logins);
        } else {
            writeDataFile.write(String.format("No date on record, filename: %s data: %s",
                    fileName, String.format("%s %s %s", username, fio, application)));

        }

    }
}
