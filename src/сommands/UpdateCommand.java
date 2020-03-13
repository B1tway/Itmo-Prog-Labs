package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

public class UpdateCommand extends Command {
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
            return false;
        }
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        sm.update(id, marine);
        return true;
    }
}
