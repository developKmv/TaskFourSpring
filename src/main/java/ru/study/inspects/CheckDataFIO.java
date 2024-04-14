package ru.study.inspects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckDataFIO implements CheckData{
    @Override
    public String check(String data) {
        Pattern searchUpperFirsChar = Pattern.compile("[A-Z,А-Я](?=[a-z,а-я])");
        Matcher matcher = searchUpperFirsChar.matcher(data);
        Pattern searchLowerFirstChar = Pattern.compile("^[a-z,а-я]|(?<=\\s)[a-z,а-я]");
        Matcher matcher2 = searchLowerFirstChar.matcher(data);

        StringBuilder sb = new StringBuilder(data);
        while(matcher2.find()){
            sb.replace(matcher2.start(),matcher2.end(),matcher2.group().toUpperCase());
        }

        return sb.toString();
    }
}
