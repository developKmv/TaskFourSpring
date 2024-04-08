package ru.study.servises;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
@Service
@Qualifier("file_source")
public class ReadDataFile implements ReadData{
    private String filePath;
    public ReadDataFile(@Value("src/test/resources/data_files/data.txt")String filePath){
        this.filePath = filePath;
    };

    @Override
    public String[] read() {
        ArrayList<String> result = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter("\n");
        while (scanner.hasNext()){
            result.add(scanner.next());
        }
        scanner.close();
        return result.toArray(new String[result.size()]);
    }
}
