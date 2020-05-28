package сommands;

import сollection.SpaceManager;

import java.io.IOException;

/**
 * The type Remove lower key command.
 */
public class RemoveLowerKeyCommand extends Command {
    /**
     * Instantiates a new Remove lower key command.
     */
    public RemoveLowerKeyCommand() {
        super("remove_lower_key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
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
        String owner = getCollectionManager().findOwner(key);
        if(owner.equals(getUser().getName())) {
            return sm.removeLowerKey(key);
        }
        getUserHandler().writeln("Это не ваш обьект");
        return false;


    }
}
