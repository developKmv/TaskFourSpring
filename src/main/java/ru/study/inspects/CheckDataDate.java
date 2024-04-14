package ru.study.inspects;

public class CheckDataDate implements CheckData{
    @Override
    public String check(String data) {
       if(data.equals("")) return "null";
       return data;
    }
}
