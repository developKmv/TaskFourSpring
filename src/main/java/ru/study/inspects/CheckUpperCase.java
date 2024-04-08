package ru.study.inspects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUpperCase {
    public static String checkAndChangeUpper(String str){
        Pattern searchUpperFirsChar = Pattern.compile("[A-Z,А-Я](?=[a-z,а-я])");
        Matcher matcher = searchUpperFirsChar.matcher(str);
        Pattern searchLowerFirstChar = Pattern.compile("^[a-z,а-я]|(?<=\\s)[a-z,а-я]");
        Matcher matcher2 = searchLowerFirstChar.matcher(str);

        if (matcher.find())return str;
        return "";
    }
}
