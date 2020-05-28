package сommands;

import utils.DataBaseManager;

import java.io.IOException;

public class RegisterCommand extends Command {

    protected RegisterCommand() {
        super("register","");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        DataBaseManager dbManager = getUserHandler().getDataBaseManager();
        if(dbManager.addUser(user)) {
            getUserHandler().writeln("Вы успешно зарегестрировались");
            return true;
        }
        getUserHandler().writeln("Ошибка регистрации");
        return false;
    }

    @Override
    public void readArgs() {
        String userName = getUserHandler().readLineWithMessage("Введите %username%", false);
        String password = getUserHandler().readLineWithMessage("Введите password", false);
        setUser(userName, password);
    }
}
