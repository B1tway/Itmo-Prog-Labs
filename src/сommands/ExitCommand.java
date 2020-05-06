package сommands;

import java.io.IOException;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        System.exit(0);
        return false;
    }
}
