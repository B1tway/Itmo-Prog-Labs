package сommands;


import utils.UserHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * The type Command manager.
 */
public class CommandManager {
    private HashMap<String, Command> commands;
    private UserHandler userHandler;

    /**
     * Gets user handler.
     *
     * @return the user handler
     */
    public UserHandler getUserHandler() {
        return userHandler;
    }

    private void addCommand(Command command) {
        command.setCmdManager(this);
        commands.put(command.getCommandName(), command);
    }

    /**
     * Instantiates a new Command manager.
     */
    public CommandManager() {
        commands = new HashMap<>();
        addCommand(new HelpCommand());
        addCommand(new InfoCommand());
        addCommand(new RemoveKeyCommand());
        addCommand(new UpdateCommand());
        addCommand(new InsertCommand());
        addCommand(new ClearCommand());
        addCommand(new ShowCommand());
        addCommand(new SumOfHealthCommand());
        addCommand(new ReplaceIfGreaterCommand());
        addCommand(new RemoveLowerKeyCommand());
        addCommand(new RemoveGreaterCommand());
        addCommand(new FilterByCategoryCommand());
        addCommand(new CountGreaterThanWeaponTypeCommand());
        addCommand(new SaveCommand());
        addCommand(new LoadCommand());
        addCommand(new ExecuteCommand());
        addCommand(new ExitCommand());

    }

    /**
     * Gets command.
     *
     * @param cmdName the cmd name
     * @return the command
     */
    public Command getCommand(String cmdName) {
        for (Command cmd : commands.values()) {
            if (cmd.getCommandName().equals(cmdName)) return cmd;
        }
        return new EmptyCommand();
    }

    /**
     * Execute command.
     *
     * @param cmdName the cmd name
     * @param args    the args
     * @throws IOException the io exception
     */
    public boolean executeCommand(String cmdName, String[] args) throws IOException {
       return getCommand(cmdName).execute(args);
    }

    /**
     * Get commands object [ ].
     *
     * @return the object [ ]
     */
    public Object[] getCommands() {
        return commands.values().toArray();
    }

    /**
     * Sets user handler.
     *
     * @param userHandler the user handler
     */
    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

}
