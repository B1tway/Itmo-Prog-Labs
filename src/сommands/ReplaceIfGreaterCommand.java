package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

/**
 * The type Replace if greater command.
 */
public class ReplaceIfGreaterCommand extends Command {
    /**
     * Instantiates a new Replace if greater command.
     */
    public ReplaceIfGreaterCommand() {
        super("replace_if_greater", "заменить значение по ключу, если новое значение больше старого");
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

        SpaceMarine spaceMarine = (SpaceMarine) argsObject.get(0);
        if(spaceMarine.getUserName().equals(getUser().getName())) {
            sm.replaceIfGreaterThan(key, spaceMarine);
            getUserHandler().writeln("Элемент заменен");
            return true;
        }
        getUserHandler().writeln("Это не ваш обьект");
        return false;


    }

    @Override
    public void readArgs() {
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        argsObject.add(marine);
    }
}
