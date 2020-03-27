package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

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
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        spaceManager.removeGreaterThan(marine);
        return true;
    }
}
