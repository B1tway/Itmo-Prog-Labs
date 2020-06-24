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
        setInput(true);
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        String key = null;
        try {
            key = args[0];
        } catch (ArrayIndexOutOfBoundsException exp) {
            getUserHandler().writeln("Неверные параметры, введите ключ");
            return true;
        }
        System.out.println(key);
        if (key == null) {
            getUserHandler().writeln("Что-то с ключом");
        }
        if (sm.contains(key)) {
            getUserHandler().writeln("Элемент с таким ключем уже содержится");
            return true;
        }
        SpaceMarine spaceMarine = null;
        if (argsObject.size() == 0) spaceMarine = sm.getHandler().readSpaceMarine();
        else spaceMarine = (SpaceMarine) argsObject.get(0);
        sm.insert(key, spaceMarine);
        getUserHandler().writeln("Элемент вставлен");
        return true;

    }

    @Override
    public void readArgs() throws IOException {
        argsObject.clear();
        SpaceMarine marine = null;
        if (getUserHandler().getClient().getController() == null) {
            marine = getUserHandler().readSpaceMarine();
            if (user != null) marine.setUser(getUser().getName());
            argsObject.add(marine);
        } else {
            getUserHandler().getClient().getController().inputSpaceMarine(this);
        }


    }
}
