package сommands;

import java.io.IOException;

public class UpdateCommand extends Command {
    public UpdateCommand() {
        super("update id {element}","обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        return false;
    }
}
