package сollection;


import java.util.List;

/**
 * The interface Storage manager.
 */
public interface StorageManager {
    /**
     * Info string.
     *
     * @return the string
     */
    String info();

    /**
     * Update.
     *
     * @param id          the id
     * @param spaceMarine the space marine
     */
    void update(int id, SpaceMarine spaceMarine);

    /**
     * Save.
     */
    void save();

    /**
     * Remove greater than.
     *
     * @param spaceMarine the space marine
     */
    void removeGreaterThan(SpaceMarine spaceMarine);

    /**
     * Replace if greater than.
     *
     * @param key         the key
     * @param spaceMarine the space marine
     */
    void replaceIfGreaterThan(String key, SpaceMarine spaceMarine);

    /**
     * Remove lower key.
     *
     * @param key the key
     */
    boolean removeLowerKey(String key);

    /**
     * Sum of health long.
     *
     * @return the long
     */
    float sumOfHealth();

    /**
     * Count greater than weapon long.
     *
     * @param weapon the weapon
     * @return the long
     */
    long countGreaterThanWeapon(Weapon weapon);

    /**
     * Find by id space marine.
     *
     * @param id the id
     * @return the space marine
     */
    SpaceMarine findById(int id);

    /**
     * Filter by category list.
     *
     * @param category the category
     * @return the list
     */
    List<SpaceMarine> filterByCategory(AstartesCategory category);


}
