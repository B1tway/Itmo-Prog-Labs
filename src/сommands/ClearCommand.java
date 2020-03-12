package сommands;

import java.io.IOException;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear","очистить коллекцию");
    }
    @Override
    public boolean execute(String[] args) throws IOException {
        var spaceManager = getCollectionManager();
        spaceManager.clear();
        return true;
    }
}
