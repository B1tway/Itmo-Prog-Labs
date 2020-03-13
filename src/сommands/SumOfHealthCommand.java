package сommands;

import сollection.SpaceManager;

import java.io.IOException;

public class SumOfHealthCommand extends Command {
    public SumOfHealthCommand() {
        super("sum_of_health", "вывести сумму значений поля health для всех элементов коллекции");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        System.out.println(sm.sumOfHealth());
        return true;
    }
}
