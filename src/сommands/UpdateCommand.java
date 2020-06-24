package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;
import java.sql.SQLException;

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
    public boolean execute(String[] args) throws IOException, SQLException {
        SpaceManager sm = getCollectionManager();
        int id = 0;
        try {
            id = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exp) {
            getUserHandler().writeln("Неверные параметры, введите id");
            return false;
        }
        if (!sm.containsId(id)) {
            getUserHandler().writeln("Элемента с таким id нет в коллекции");
            return false;
        }
        SpaceMarine marine = (SpaceMarine) argsObject.get(0);
        if(marine.getUserName().equals(getUser().getName())) {
            sm.update(id,marine);
            getUserHandler().writeln("Обьект обновлен");
            return true;
        }
        else {
            getUserHandler().writeln("Это не ваш обьект");
        }
        return false;
    }

    @Override
    public void readArgs() {
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        argsObject.add(marine);
    }
}
