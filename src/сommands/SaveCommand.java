package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save", "сохранить коллекцию в файл");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        SpaceManager sm = getCollectionManager();
        SpaceMarine[] marines = sm.getMarines();
        for(var marine: marines) {
            strings.add(marine.toString());
        }

    }
}
