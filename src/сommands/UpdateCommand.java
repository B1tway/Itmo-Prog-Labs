package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

/**
 * The type Update command.
 */
public class UpdateCommand extends Command {
    /**
     * Instantiates a new Update command.
     */
    public UpdateCommand() {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        int id = 0;
        try {
            id = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exp) {
            System.out.println("Неверные параметры, введите id");
            return true;
        }
        if (!sm.containsId(id)) {
            System.out.println("Элемента с таким id нет в коллекции");
            return true;
        }
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        sm.update(id, marine);
        System.out.println("Обьект обновлен");
        return true;
    }
}
