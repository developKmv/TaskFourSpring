package ru.study.inspects;

import ru.study.servises.WriteDataFile;
import ru.study.utils.LogTransformation;

import java.sql.Timestamp;

public interface CheckData {
    @LogTransformation
    String checkAndChangeUpper(String str);
    @LogTransformation
    String checkApplicationName(String app);
    @LogTransformation
    Timestamp createDate(String date);
    @LogTransformation
    boolean checkExistDate(String str, String data, String fileName, WriteDataFile wdf);

}
