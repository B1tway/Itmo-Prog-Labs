package сommands;

import utils.CSVLoader;
import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save", "сохранить коллекцию в файл");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        String filename = "data.csv";
        ArrayList<String[]> strings = new ArrayList<>();
        SpaceManager sm = getCollectionManager();
        SpaceMarine[] marines = sm.getMarines();
        for (var marine : marines) {
            String[] tokens = marine.toString().split(" ; ");
            tokens = Arrays.copyOf(tokens, tokens.length + 1);
            tokens[tokens.length - 1] = sm.findKey(marine);
            strings.add(tokens);
        }
        CSVLoader csvLoader = new CSVLoader(filename);
        csvLoader.writeToCsvFile(strings, "_:kek:_");
        return true;

    }
}
