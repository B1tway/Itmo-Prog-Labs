package сollection;

import сommands.Command;

import java.io.IOException;

/**
 * The type Exit command.
 */
public class ExitCommand extends Command {

    /**
     * Instantiates a new Exit command.
     */
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        return false;
    }
}
