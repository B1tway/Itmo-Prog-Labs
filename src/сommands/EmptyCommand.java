package сommands;

import java.io.IOException;

public class EmptyCommand extends Command {
    public EmptyCommand() {
        super("empty_command","ничего не делает");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        System.out.println("Такой комманды не существует");
        return true;
    }
}
