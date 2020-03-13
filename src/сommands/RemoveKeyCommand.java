package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

public class RemoveKeyCommand extends Command {
    public RemoveKeyCommand() {
        super("remove_key"," удалить из коллекции все элементы, ключ которых меньше, чем заданный");
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

        sm.remove(key);
        return true;
    }
}
