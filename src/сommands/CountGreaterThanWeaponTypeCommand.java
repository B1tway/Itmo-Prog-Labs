package сommands;

import сollection.SpaceManager;
import сollection.Weapon;

import java.io.IOException;

public class CountGreaterThanWeaponTypeCommand extends Command{
    public CountGreaterThanWeaponTypeCommand() {
        super("count_greater_than_weapon_type", "вывести количество элементов, значение поля weaponType которых больше заданного");
    }
    @Override
    public boolean execute(String[] args) throws IOException {
        SpaceManager sm = getCollectionManager();
        Weapon weapon = getUserHandler().readWeapon();
        System.out.println(sm.countGreaterThanWeapon(weapon));
        return true;
    }
}
