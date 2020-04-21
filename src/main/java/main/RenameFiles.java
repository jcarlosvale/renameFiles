package main;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class RenameFiles {
    public static void main(String[] args) {
        String path = args[0];
        String csvFileName = args[1];
        renameFiles(path,csvFileName);
    }

    private static void renameFiles(String path, String csvFileName) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFileName));
            String[] line;
            while ((line = reader.readNext()) != null) {
                String testID = line[0];
                String oldFileName = line[1] + ".xml";
                String newFileName = line[2] + ".xml";
                copyFile(path, oldFileName, newFileName);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }

    private static void copyFile(String path, String oldFileName, String newFileName) throws IOException {
        Files.copy((new File(path+oldFileName)).toPath(), (new File(path+newFileName).toPath()));
    }
}
