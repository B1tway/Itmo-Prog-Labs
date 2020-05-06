package сommands;

import utils.Handler;
import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

/**
 * The type Replace if greater command.
 */
public class ReplaceIfGreaterCommand extends Command {
    /**
     * Instantiates a new Replace if greater command.
     */
    public ReplaceIfGreaterCommand() {
        super("replace_if_greater","заменить значение по ключу, если новое значение больше старого");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        String key = "";
        try {
            key = args[0];
        } catch (ArrayIndexOutOfBoundsException exp) {
            getUserHandler().writeln("Неверные параметры, введите ключ");
            return true;
        }

        SpaceMarine spaceMarine = sm.getHandler().readSpaceMarine();
        sm.replaceIfGreaterThan(key,spaceMarine);
        return true;
    }

    @Override
    public void readArgs() {
        SpaceMarine marine = getUserHandler().readSpaceMarine();
        argsObject.add(marine);
    }
}
