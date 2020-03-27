package сommands;

import utils.UserHandler;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Scanner;
import java.util.Set;

/**
 * The type Execute command.
 */
public class ExecuteCommand extends Command {
    /**
     * Instantiates a new Execute command.
     */

    public ExecuteCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        UserHandler uh = getUserHandler();
        uh.setInteractive(false);
        uh.upStackCount();
        Set<String> files = uh.getFiles();
//        if (uh.getStackCount() > uh.getStackSize()) {
//            System.out.println("StackOverflow");
//            return false;
//        }
        String fileName = "";
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException exp) {
            uh.writeln("Введите имя файла");
            return false;
        }

        try {
            Scanner old = uh.getScanner();
            if (files.contains(fileName)) {
                System.out.println("Возникла зависимость");
            }
            files.add(fileName);
            uh.setScanner(new Scanner(new File(fileName)));
            while (uh.getScanner().hasNextLine()) {
                if (!uh.nextCommand()) break;
            }
            uh.setScanner(old);
        } catch (Exception exp) {
            System.out.println("Файл не найден или недоступен");
        }
        uh.downStackCount();
        uh.setInteractive(true);
        uh.getFiles().remove(fileName);
        return true;
    }
}
