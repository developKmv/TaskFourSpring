package ru.study.inspects;

import org.springframework.stereotype.Component;
import ru.study.servises.WriteDataFile;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component("checkDataImp")
public class CheckDataImpl implements CheckData{
    public String checkAndChangeUpper(String str){
        Pattern searchUpperFirsChar = Pattern.compile("[A-Z,А-Я](?=[a-z,а-я])");
        Matcher matcher = searchUpperFirsChar.matcher(str);
        Pattern searchLowerFirstChar = Pattern.compile("^[a-z,а-я]|(?<=\\s)[a-z,а-я]");
        Matcher matcher2 = searchLowerFirstChar.matcher(str);

        StringBuilder sb = new StringBuilder(str);
        while(matcher2.find()){
            sb.replace(matcher2.start(),matcher2.end(),matcher2.group().toUpperCase());
        }

        return sb.toString();
    }
    public String checkApplicationName(String app){
        Pattern pattern = Pattern.compile("web|mobile");
        Matcher matcher = pattern.matcher(app);

        while (matcher.find()){
            return app;
        }

        return "other: " + app;
    }
    public Timestamp createDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date res;
        try {
            res = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Timestamp(res.getTime());
    }
    public boolean checkExistDate(String str, String data, String fileName, WriteDataFile wdf){
        if(str.equals("")){
            wdf.write(String.format("No date on record, filename: %s record: %s",fileName,data));
            return false;
        }
        return true;
    }
}
