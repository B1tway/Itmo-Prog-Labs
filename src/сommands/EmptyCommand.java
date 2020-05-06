package сommands;

import java.io.IOException;

/**
 * The type Empty command.
 */
public class EmptyCommand extends Command {
    /**
     * Instantiates a new Empty command.
     */
    public EmptyCommand() {
        super("empty_command","ничего не делает");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        getUserHandler().writeln("Такой команды не существует");
        return true;
    }
}
