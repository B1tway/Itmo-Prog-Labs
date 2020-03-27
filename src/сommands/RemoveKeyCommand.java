package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

/**
 * The type Remove key command.
 */
public class RemoveKeyCommand extends Command {
    /**
     * Instantiates a new Remove key command.
     */
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
            return true;
        }
        if(sm.contains(key)) System.out.println("Элемент удален успешно");
        else System.out.println("Элемент с таким ключем не существует");
        sm.remove(key);
        return true;
    }
}
