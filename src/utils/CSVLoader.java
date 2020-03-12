package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVLoader {
    String fileName;

    public CSVLoader(String filename) {
        this.fileName = filename;
    }

    public void writeToCsvFile(List<String[]> thingsToWrite, String separator) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String[] strings : thingsToWrite) {
                for (int i = 0; i < strings.length; i++) {
                    writer.append(strings[i]);
                    if (i < (strings.length - 1))
                        writer.append(separator);
                }
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> readFromCsvFile(String separator) {
        List<String[]> list = new ArrayList<>();
        try (Scanner reader = new Scanner(new FileReader(fileName))) {

            String line = "";
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                String[] array = line.split(separator);
                list.add(array);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
    }
}
