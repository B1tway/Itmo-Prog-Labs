package сommands;

import сollection.SpaceManager;

import java.io.IOException;

/**
 * The type Remove key command.
 */
public class RemoveKeyCommand extends Command {
    /**
     * Instantiates a new Remove key command.
     */
    public RemoveKeyCommand() {
        super("remove_key", " удалить из коллекции все элементы, ключ которых меньше, чем заданный");
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
        if(getCollectionManager().findOwner(key).equals(user.getName())) {
            if (sm.contains(key)) getUserHandler().writeln("Элемент удален успешно");
            else getUserHandler().writeln("Элемент с таким ключем не существует");
            sm.remove(key);
            return true;
        }
        getUserHandler().writeln("Это не ваш обьект");
        return false;
    }
}
