package сommands;

import utils.UserHandler;
import сollection.SpaceManager;

import java.io.IOException;

/**
 * The type Command.
 */
public abstract class Command {
    /**
     * The Cmd name.
     */
    protected String cmdName;
    /**
     * The Args.
     */
    protected String[] args;
    /**
     * The Help text.
     */
    protected String helpText;
    /**
     * The Args count.
     */
    protected int argsCount = 0;
    private CommandManager cmdManager;

    /**
     * Instantiates a new Command.
     *
     * @param cmdName  the cmd name
     * @param helpText the help text
     */
    protected Command(String cmdName, String helpText) {
        this.cmdName = cmdName;
        this.helpText = helpText;
    }

    /**
     * Execute boolean.
     *
     * @param args the args
     * @return the boolean
     * @throws IOException the io exception
     */
    public abstract boolean execute(String args[]) throws IOException;

    /**
     * Gets collection manager.
     *
     * @return the collection manager
     */
    public SpaceManager getCollectionManager() {
        return getCmdManager().getUserHandler().getSpaceManager();
    }

    /**
     * Gets user handler.
     *
     * @return the user handler
     */
    public UserHandler getUserHandler() {
        return getCmdManager().getUserHandler();
    }

    /**
     * Sets cmd manager.
     *
     * @param cmdManager the cmd manager
     */
    public void setCmdManager(CommandManager cmdManager) {
        this.cmdManager = cmdManager;
    }

    /**
     * Gets cmd manager.
     *
     * @return the cmd manager
     */
    public CommandManager getCmdManager() {
        return cmdManager;
    }

    /**
     * Gets command name.
     *
     * @return the command name
     */
    public String getCommandName() {
        return cmdName;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return helpText;
    }
}
