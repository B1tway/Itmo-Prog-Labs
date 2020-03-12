package сollection;

import сommands.Command;

import java.io.IOException;

public class ShowCommand extends Command {
    public ShowCommand() {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        sm.show();
        return true;
    }
}
