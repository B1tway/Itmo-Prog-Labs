package сommands;

import java.io.IOException;
import java.io.StringReader;

/**
 * The type Help command.
 */
public class HelpCommand extends Command {

    /**
     * Instantiates a new Help command.
     */
    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        CommandManager cmdManager = getCmdManager();
        Object[] commands = cmdManager.getCommands();
        for (int i = 0; i < commands.length; i++) {
            Command current = (Command) commands[i];
            System.out.println(current.getCommandName() + " : " + current.getInfo());
        }
        return true;
    }
}
