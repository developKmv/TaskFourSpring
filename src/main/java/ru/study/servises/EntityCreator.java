package ru.study.servises;

import org.springframework.stereotype.Service;
import ru.study.entity.Logins;
import ru.study.entity.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntityCreator<U,L> {
    public static List<Object> createUserLogins(String data){
        List<Object> result = new ArrayList<>();
        String arrData[] = data.split(";");

        User user = new User(null,arrData[0],arrData[1]);
        Logins logins = new Logins(null,new Timestamp(System.currentTimeMillis()),arrData[2],user);

        return result;
    }
}
