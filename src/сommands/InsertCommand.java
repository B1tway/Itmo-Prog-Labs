package сommands;

import сollection.SpaceManager;
import сollection.SpaceMarine;

import java.io.IOException;

public class InsertCommand extends Command {
    public InsertCommand() {
        super("insert null {element}", "добавить новый элемент с заданным ключом");
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        return true;
    }
}
