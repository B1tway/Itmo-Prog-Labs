package сommands;

import utils.CSVLoader;
import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Save command.
 */
public class SaveCommand extends Command {
    /**
     * Instantiates a new Save command.
     */
    public SaveCommand() {
        super("save", "сохранить коллекцию в файл");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        String filename = System.getenv("INPUT_PATH");
        if (filename == null) {
            System.out.println("Переменная не найдена");
            System.exit(0);
        }
        Path path = Paths.get(filename);
        if (!(Files.isReadable(path) && Files.isExecutable(path) && Files.isWritable(path))) {
            System.out.println("Ошибка прав");
            System.exit(0);
        }
        ArrayList<String[]> strings = new ArrayList<>();
        SpaceManager sm = getCollectionManager();
        SpaceMarine[] marines = sm.getMarines();
        for (SpaceMarine marine : marines) {
            String[] tokens = marine.toString().split(" ; ");
            tokens = Arrays.copyOf(tokens, tokens.length + 1);
            tokens[tokens.length - 1] = sm.findKey(marine);
            strings.add(tokens);
        }
        CSVLoader csvLoader = new CSVLoader(filename);
        csvLoader.writeToCsvFile(strings, "_:kek:_");
        System.out.println("Коллекция сохранена");
        return true;

    }
}
