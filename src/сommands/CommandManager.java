package сommands;


import utils.UserHandler;
import сollection.ExitCommand;
import сollection.ShowCommand;

import java.io.IOException;
import java.util.HashMap;

public class CommandManager {
    private HashMap<String, Command> commands;
    private UserHandler userHandler;

    public UserHandler getUserHandler() {
        return userHandler;
    }

    private void addCommand(Command command) {
        command.setCmdManager(this);
        commands.put(command.getCommandName(), command);
    }

    public CommandManager() {
        commands = new HashMap<>();
        addCommand(new HelpCommand());
        addCommand(new InfoCommand());
        addCommand(new RemoveKeyCommand());
        addCommand(new UpdateCommand());
        addCommand(new InsertCommand());
        addCommand(new ClearCommand());
        addCommand(new ShowCommand());
        addCommand(new ExitCommand());
        addCommand(new SumOfHealthCommand());
        addCommand(new ReplaceIfGreaterCommand());
        addCommand(new RemoveLowerKeyCommand());
        addCommand(new RemoveGreaterCommand());
        addCommand(new FilterByCategoryCommand());
        addCommand(new CountGreaterThanWeaponTypeCommand());
    }

    public Command getCommand(String cmdName) {
        for (Command cmd : commands.values()) {
            if (cmd.getCommandName().equals(cmdName)) return cmd;
        }
        return new EmptyCommand();
    }

    public void executeCommand(String cmdName, String[] args) throws IOException {
        getCommand(cmdName).execute(args);
    }

    public Object[] getCommands() {
        return commands.values().toArray();
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

}
