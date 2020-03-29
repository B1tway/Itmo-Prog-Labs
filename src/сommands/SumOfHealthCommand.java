package сommands;

import сollection.SpaceManager;

import java.io.IOException;

/**
 * The type Sum of health command.
 */
public class SumOfHealthCommand extends Command {
    /**
     * Instantiates a new Sum of health command.
     */
    public SumOfHealthCommand() {
        super("sum_of_health", "вывести сумму значений поля health для всех элементов коллекции");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        System.out.println("Сумма жизни всех: " + sm.sumOfHealth());
        return true;
    }
}
