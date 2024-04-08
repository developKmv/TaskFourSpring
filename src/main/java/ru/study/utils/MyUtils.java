package ru.study.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    public static int extraCharacterIndex(String str){
        Pattern p = Pattern.compile("\r");
        Matcher matcher = p.matcher(str);
        int deleteR = matcher.find() ? matcher.start() : str.length()-1;
        return deleteR;
    }
}
