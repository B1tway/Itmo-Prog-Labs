package сommands;

import сollection.SpaceManager;
import сommands.Command;

import java.io.IOException;

/**
 * The type Show command.
 */
public class ShowCommand extends Command {
    /**
     * Instantiates a new Show command.
     */
    public ShowCommand() {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        String header = " key; id; name; Coordinates; Date; health; category; weapon; meleeWeapon; Chapter;";
        getUserHandler().writeln(header);
        sm.show();
        return true;
    }
}
