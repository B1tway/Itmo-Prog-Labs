package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

public class RemoveGreaterCommand extends Command {
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
