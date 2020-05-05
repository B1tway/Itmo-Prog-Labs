package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

/**
 * The type Insert command.
 */
public class InsertCommand extends Command {
    /**
     * Instantiates a new Insert command.
     */
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
            return true;
        }
        if (sm.contains(key)) {
            System.out.println("Элемент с таким ключем уже содержится");
            return true;
        }
        SpaceMarine spaceMarine = sm.getHandler().readSpaceMarine();

        sm.insert(key, spaceMarine);

        return true;
    }

    @Override
    public void readArgs() {
        argsObject.clear();
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        argsObject.add(marine);

    }
}
