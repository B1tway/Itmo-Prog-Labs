package сommands;

import jdk.jfr.Category;
import сollection.AstartesCategory;
import сollection.SpaceManager;
import сollection.SpaceMarine;
import сollection.Weapon;

import java.io.IOException;

public class FilterByCategoryCommand extends Command {
    public FilterByCategoryCommand() {
        super("filter_by_category", "вывести элементы, значение поля category которых равно заданному");
    }

    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        AstartesCategory category = getUserHandler().readCategory();
        var list = sm.filterByCategory(category);
        if(list.size() == 0) {
            System.out.println("Таких элементов нет");
            return false;
        }
        for (SpaceMarine marine : list) {
            System.out.println(marine.toString());
        }
        return true;
    }
}
