package сommands;

import network.client.User;
import utils.DataBaseManager;

import java.io.IOException;

public class LoginCommand extends Command {
    /**
     * Instantiates a new Command.
     *
     * @param cmdName  the cmd name
     * @param helpText the help text
     */
    protected LoginCommand(String cmdName, String helpText) {
        super(cmdName, helpText);
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        DataBaseManager dbManager = getUserHandler().getDataBaseManager();
        if(dbManager.containsUser(user)) return true;
        return false;
    }

    @Override
    public void readArgs() {
        String userName = getUserHandler().readLineWithMessage("Введите %username%", false);
        String password = getUserHandler().readLineWithMessage("Введите password", false);
        setUser(userName, password);
    }
}
