package сommands;

import java.io.IOException;

public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        var sp = getCollectionManager();
        System.out.println(sp.info());
        return true;
    }
}
