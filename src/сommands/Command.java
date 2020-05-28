package сommands;

import network.client.User;
import utils.Handler;
import сollection.SpaceManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Command.
 */
public abstract class Command implements Serializable {
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
    transient protected String helpText;
    protected User user;
    /**
     * The Args count.
     */
    protected int argsCount = 0;
    protected List<Object> argsObject;
    transient private CommandManager cmdManager;
    /**
     * Instantiates a new Command.
     *
     * @param cmdName  the cmd name
     * @param helpText the help text
     */
    protected Command(String cmdName, String helpText) {
        this.cmdName = cmdName;
        this.helpText = helpText;
        this.argsObject = new ArrayList<>();
    }

    /**
     * Execute boolean.
     *
     * @param args the args
     * @return the boolean
     * @throws IOException the io exception
     */
    public abstract boolean execute(String args[]) throws IOException;
    public void setUser(String userName, String pass) {
        user = new User(userName, pass);

    }
    public String[] getArgs() {
        return args;
    }

    /**
     * Gets collection manager.
     *
     * @return the collection manager
     */

    public SpaceManager getCollectionManager() {
        return getCmdManager().getHandler().getSpaceManager();
    }

    /**
     * Gets user handler.
     *
     * @return the user handler
     */
    public Handler getUserHandler() {
        return getCmdManager().getHandler();
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
     * Sets cmd manager.
     *
     * @param cmdManager the cmd manager
     */
    public void setCmdManager(CommandManager cmdManager) {
        this.cmdManager = cmdManager;
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

    public void readArgs() {
        return;
    }

    public List<Object> getArgsObject() {
        return argsObject;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
