package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;
import сollection.Weapon;

import java.io.IOException;

/**
 * The type Remove greater command.
 */
public class RemoveGreaterCommand extends Command {
    /**
     * Instantiates a new Remove greater command.
     */
    public RemoveGreaterCommand() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
    }
    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager spaceManager = getCollectionManager();
        SpaceMarine marine = (SpaceMarine) argsObject.get(0);
        spaceManager.removeGreaterThan(marine);
        return true;
    }

    @Override
    public void readArgs() {
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        argsObject.add(marine);
    }
}
