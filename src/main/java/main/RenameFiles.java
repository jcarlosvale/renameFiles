package main;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public class RenameFiles {
    public static void main(String[] args) {
        String path = args[0]+"\\";
        String csvFileName = args[1];
        renameFiles(path,csvFileName);
        System.out.println("***END OF EXECUTION***");
    }

    private static void renameFiles(String path, String csvFileName) {
        Set<String> filesToRemove = new HashSet<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvFileName))){
            String[] line;
            while ((line = reader.readNext()) != null) {
                String testID = line[0];
                String oldFileName = line[1] + ".xml";
                String newFileName = line[2] + ".xml";
                System.out.println(oldFileName + " ----> " + newFileName);
                copyFile(path, oldFileName, newFileName);
                filesToRemove.add(oldFileName);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        for (String filename: filesToRemove) {
            System.out.println("REMOVING " + filename);
            try {
                deleteFile(path,filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void deleteFile(String path, String oldFileName) throws IOException {
        Files.delete((new File(path+oldFileName)).toPath());
    }

    private static void copyFile(String path, String oldFileName, String newFileName) throws IOException {
        Files.copy(
                (new File(path+oldFileName)).toPath(),
                (new File(path+newFileName).toPath()),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
