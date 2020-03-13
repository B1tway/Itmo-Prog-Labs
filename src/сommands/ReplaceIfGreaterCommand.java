package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

public class ReplaceIfGreaterCommand extends Command {
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
            System.out.println("Неверные параметры, введите ключ");
            return false;
        }

        SpaceMarine spaceMarine = sm.getUserHandler().readSpaceMarine();
        sm.replaceIfGreaterThan(key,spaceMarine);
        return true;
    }

}
