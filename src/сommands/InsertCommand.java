package сommands;

import network.client.User;
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
            getUserHandler().writeln("Неверные параметры, введите ключ");
            return true;
        }
        if (sm.contains(key)) {
            getUserHandler().writeln("Элемент с таким ключем уже содержится"); 
            return true;
        }
        SpaceMarine spaceMarine = null;
        if (argsObject.size() == 0) spaceMarine = sm.getHandler().readSpaceMarine();
        spaceMarine = (SpaceMarine) argsObject.get(0);
        if(true) {
            sm.insert(key, spaceMarine);
            getUserHandler().writeln("Элемент вставлен");
            return true;
        }
        getUserHandler().writeln("Это не ваш обьект");
        return false;
    }

    @Override
    public void readArgs() {
        argsObject.clear();
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        if(user != null) marine.setUser(getUser().getName());
        argsObject.add(marine);

    }
}
