package сommands;

import utils.CSVLoader;
import utils.Parser;
import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class LoadCommand extends Command {
    public LoadCommand() {
        super("load", "загружает коллекцию из файла");
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        String filename = "data.csv";
        Parser parser = new Parser();
        CSVLoader csvLoader = new CSVLoader(filename);
        var list = csvLoader.readFromCsvFile("_:kek:_");
        String source = "";
        var spaceManager = getUserHandler().getSpaceManager();
        spaceManager.clear();
        for (String[] strings : list) {
            try {
                SpaceMarine marine = (parser.parseSpaceMarine(strings));
                spaceManager.insert(strings[strings.length - 1], marine);

            } catch (Exception e) {
                System.out.println("Файл поврежден или не найден");
            }
        }
        return true;
    }
}
