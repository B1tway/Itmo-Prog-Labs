package сommands;

import utils.CSVLoader;
import utils.Parser;
import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The type Load command.
 */
public class LoadCommand extends Command {
    /**
     * Instantiates a new Load command.
     */
    public LoadCommand() {
        super("load", "загружает коллекцию из файла");
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        String filename = null;

        filename = System.getenv("INPUT_PATH");
        if (filename == null) {
            System.out.println("Переменная не найдена");
            System.exit(0);
        }
        Path path = Paths.get(filename);
        if (!(new File(String.valueOf(path)).isFile())) {
            System.out.println("Файл не существует");
            System.exit(0);
        }
        if (!(Files.isReadable(path) && Files.isWritable(path))) {
            System.out.println("Ошибка прав");
            System.exit(0);
        }


        Parser parser = new Parser();
        CSVLoader csvLoader = new CSVLoader(filename);
        List<String[]> list = csvLoader.readFromCsvFile("_:kek:_");
        String source = "";
        SpaceManager spaceManager = getUserHandler().getSpaceManager();
        spaceManager.clear();
        int cnt = 0;
        for (String[] strings : list) {
            boolean incorrect = false;
            try {
                SpaceMarine marine = (parser.parseSpaceMarine(strings));
                if (marine.getId() <= 0) {
                    System.out.println("Некорректный id");
                    incorrect = true;
                    continue;
                }
                if (marine.getChapter().getCount() != null && (marine.getChapter().getCount() < 0 || marine.getChapter().getCount() > 1000)) {
                    System.out.println("Некорректный marineCount");
                    incorrect = true;
                    continue;
                }
                if (marine.getHealth() < 0) {
                    System.out.println("Некорректный health");
                    cnt++;
                    continue;
                }
                if (spaceManager.contains(strings[strings.length - 1])) {
                    System.out.println("Элемент с таким ключем уже был добавлен");
                    cnt++;
                    continue;
                }
                if (spaceManager.containsId(marine.getId())) {
                    System.out.println("Элемент с таким id уже был добавлен");
                    cnt++;
                    continue;
                }
                if (marine.getWeaponType() == null) {
                    System.out.println("WeaponType не может быть равен null");
                }
                if (marine.getMeleeWeapon() == null) {
                    System.out.println("MeleeWeaponType не может быть равен null");
                }
                spaceManager.insert(strings[strings.length - 1], marine);

            } catch (Exception e) {
                System.out.println("Некорректное содержание файла");
                cnt++;
            }

        }
        System.out.println("Коллекция загружена из файла");
        if (cnt > 0) {
            System.out.println("Не было загружено " + cnt + " обьектов");
        }
        return true;
    }
}
