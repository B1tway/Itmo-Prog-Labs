package сommands;

import java.io.IOException;

public class RemoveKeyCommand extends Command {
    public RemoveKeyCommand() {
        super("remove_lower_key null","удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        return false;
    }
}
