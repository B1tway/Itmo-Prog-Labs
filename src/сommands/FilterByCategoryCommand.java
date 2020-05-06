package сommands;


import сollection.AstartesCategory;
import сollection.SpaceManager;
import сollection.SpaceMarine;
import сollection.Weapon;

import java.io.IOException;
import java.util.List;

/**
 * The type Filter by category command.
 */
public class FilterByCategoryCommand extends Command {
    /**
     * Instantiates a new Filter by category command.
     */
    public FilterByCategoryCommand() {
        super("filter_by_category", "вывести элементы, значение поля category которых равно заданному");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        AstartesCategory category = getUserHandler().readCategory();

        List<SpaceMarine> list = sm.filterByCategory(category);
        if(list.size() == 0) {
            getUserHandler().writeln("Таких элементов нет");
            return true;
        }
        for (SpaceMarine marine : list) {
            getUserHandler().writeln(marine.toString());
        }
        return true;
    }

    @Override
    public void readArgs() {
        AstartesCategory category = getUserHandler().readCategory();
        argsObject.add(category);
    }
}
