package сommands;


import utils.Handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * The type Command manager.
 */
public class CommandManager {
    private HashMap<String, Command> commands;
    private Handler handler;

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
        addCommand(new LoadCommand());
        addCommand(new ExecuteCommand());
        addCommand(new ExitCommand());
        addCommand(new LoginCommand());
        addCommand(new RegisterCommand());

    }

    /**
     * Gets user handler.
     *
     * @return the user handler
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * Sets user handler.
     *
     * @param handler the user handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void addCommand(Command command) {
        command.setCmdManager(this);
        commands.put(command.getCommandName(), command);
    }

    /**
     * Gets command.
     *
     * @param cmdName the cmd name
     * @return the command
     */
    public Command getCommand(String cmdName) {
        for (Command cmd : commands.values()) {
            if (cmd.getCommandName().equals(cmdName)) {
                cmd.args = new String[0];
                return cmd;
            }
        }
        return new EmptyCommand();
    }

    public Command getCommand(String cmdName, String[] args) {
        Command cmd = getCommand(cmdName);
        cmd.args = args;
        cmd.readArgs();
        return cmd;
    }

    /**
     * Execute command.
     *
     * @param cmdName the cmd name
     * @param args    the args
     * @throws IOException the io exception
     */
    public boolean executeCommand(String cmdName, String[] args) throws IOException, SQLException {
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

}
