package сommands;

import java.io.IOException;
import java.io.StringReader;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }


    @Override
    public boolean execute(String[] args) throws IOException {
        var cmdManager = getCmdManager();
        Object[] commands = cmdManager.getCommands();
        for (int i = 0; i < commands.length; i++) {
            Command current = (Command) commands[i];
            System.out.println(current.getCommandName() + " : " + current.getInfo());
        }
        return true;
    }
}
