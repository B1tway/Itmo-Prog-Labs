package сommands;

import network.client.User;
import utils.DataBaseManager;

import java.io.IOException;

public class LoginCommand extends Command {
    protected LoginCommand() {
        super("login","");
    }



    @Override
    public boolean execute(String[] args) throws IOException {
        DataBaseManager dbManager = getUserHandler().getDataBaseManager();
        if (dbManager.containsUser(user)) {
            getUserHandler().writeln("Вы успешно авторизовались");
            return true;
        }
        getUserHandler().writeln("Неверный логин или пароль");
        return false;
    }

    @Override
    public void readArgs() {
        String userName = getUserHandler().readLineWithMessage("Введите %username%", false);
        String password = getUserHandler().readLineWithMessage("Введите password", false);
        setUser(userName, password);

    }
    public void setArgs(String userName, String password) {
        setUser(userName, password);
    }
}
