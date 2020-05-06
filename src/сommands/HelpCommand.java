package сommands;

import java.io.IOException;

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
            getUserHandler().writeln(current.getCommandName() + " : " + current.getInfo());
        }
        return true;
    }
}
