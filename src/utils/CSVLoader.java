package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, работающий с csv.
 */
public class CSVLoader {
    /**
     * The File name.
     */
    String fileName;

    /**
     * Instantiates a new Csv loader.
     *
     * @param filename the filename
     */
    public CSVLoader(String filename) {
        this.fileName = filename;
    }

    /**
     * Write to csv file.
     *
     * @param thingsToWrite the things to write
     * @param separator     the separator
     */
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
            System.out.println("Файл не найден");
        }
    }

    /**
     * Read from csv file list.
     *
     * @param separator the separator
     * @return the list
     */
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
            System.out.println("Некорректные данные в файле");
            return list;
        }
    }
}
