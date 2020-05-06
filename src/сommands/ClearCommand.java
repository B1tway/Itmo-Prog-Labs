package сommands;

import сollection.SpaceManager;

import java.io.IOException;

/**
 * The type Clear command.
 */
public class ClearCommand extends Command {
    /**
     * Instantiates a new Clear command.
     */
    public ClearCommand() {
        super("clear", "очистить коллекцию");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager spaceManager = getCollectionManager();
        spaceManager.clear();
        getUserHandler().writeln("Коллекция очищена");
        return true;
    }
}
