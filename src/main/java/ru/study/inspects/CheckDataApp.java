package ru.study.inspects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckDataApp implements CheckData{
    @Override
    public String check(String data) {
        Pattern pattern = Pattern.compile("web|mobile");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()){
            return data;
        }
        return "other: " + data;
    }
}
