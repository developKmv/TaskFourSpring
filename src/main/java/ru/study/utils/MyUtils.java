package ru.study.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    public static String extraCharacterIndex(String str){
        Pattern p = Pattern.compile("\r");
        Matcher matcher = p.matcher(str);
        int deleteR = matcher.find() ? matcher.start() : str.length()-1;
        return str.substring(0,deleteR);
    }

    public static Timestamp createDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date res;
        try {
            res = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Timestamp(res.getTime());
    }
}
