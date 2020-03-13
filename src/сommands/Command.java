package сommands;

import utils.UserHandler;
import сollection.SpaceManager;

import java.io.IOException;

public abstract class Command {
    protected String cmdName;
    protected String[] args;
    protected String helpText;
    protected int argsCount = 0;
    private CommandManager cmdManager;

    protected Command(String cmdName, String helpText) {
        this.cmdName = cmdName;
        this.helpText = helpText;
    }

    public abstract boolean execute(String args[]) throws IOException;

    public SpaceManager getCollectionManager() {
        return getCmdManager().getUserHandler().getSpaceManager();
    }
    public UserHandler getUserHandler() {
        return getCmdManager().getUserHandler();
    }
    public void setCmdManager(CommandManager cmdManager) {
        this.cmdManager = cmdManager;
    }

    public CommandManager getCmdManager() {
        return cmdManager;
    }

    public String getCommandName() {
        return cmdName;
    }

    public String getInfo() {
        return helpText;
    }
}
