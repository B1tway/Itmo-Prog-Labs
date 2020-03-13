package сommands;

import utils.UserHandler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ExecuteCommand extends Command {
    public ExecuteCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        var uh = getUserHandler();
        uh.setInteractive(false);
        uh.upStackCount();
        if (uh.getStackCount() > uh.getStackSize()) {
            System.out.println("StackOverflow");
            return false;
        }
        String fileName = "";
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException exp) {
            uh.writeln("Введите имя файла");
            return false;
        }
        try {
            Scanner old = uh.getScanner();
            uh.setScanner(new Scanner(new File(fileName)));
            while (uh.getScanner().hasNextLine()) {
                uh.nextCommand();
            }
            uh.setScanner(old);
        } catch (Exception exp) {
            System.out.println("Файл не найден или недоступен");
        }
        uh.downStackCount();
        uh.setInteractive(true);
        return true;
    }
}
