package сommands;

import сollection.SpaceManager;

import java.io.IOException;

/**
 * The type Info command.
 */
public class InfoCommand extends Command {
    /**
     * Instantiates a new Info command.
     */
    public InfoCommand() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sp = getCollectionManager();
        System.out.println(sp.info());
        return true;
    }
}
