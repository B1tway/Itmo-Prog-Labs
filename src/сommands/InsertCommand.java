package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

public class InsertCommand extends Command {
    public InsertCommand() {
        super("insert", "добавить новый элемент с заданным ключом");
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        String key = "";
        try {
            key = args[0];
        } catch (ArrayIndexOutOfBoundsException exp) {
            System.out.println("Неверные параметры, введите ключ");
            return false;
        }

        SpaceMarine spaceMarine = sm.getUserHandler().readSpaceMarine();
        sm.insert(key, spaceMarine);
        return true;
    }
}
