package ru.study.servises;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Service
public class WriteDataFile {
    private String filePath;
    public WriteDataFile(@Value("src/test/resources/data_files/log.txt")String filePath){
        this.filePath = filePath;
    }

    public void write(String str){
        str ='\n' + str;
        RandomAccessFile stream = null;
        try {
            stream = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            stream.seek(stream.length());
            stream.write(str.getBytes());
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
