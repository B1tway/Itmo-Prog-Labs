package сollection;

import network.client.User;
import utils.Handler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Space manager.
 */
public class SpaceManager implements StorageManager {
    private SpaceStorage storage;
    private Handler handler;

    private User currentUser;

    /**
     * Instantiates a new Space manager.
     *
     * @param storage the storage
     */
    public SpaceManager(SpaceStorage storage) {
        this.storage = storage;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Instantiates a new Space manager.
     */
    public SpaceManager() {
        this.storage = new SpaceStorage();
    }

    /**
     * Gets user handler.
     *
     * @return the user handler
     */
    public void setCurUser(User user) {
        this.currentUser = user;
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * Sets user handler.
     *
     * @param handler the user handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * Sets storage.
     *
     * @param storage the storage
     */
    public void setStorage(SpaceStorage storage) {
        this.storage = storage;
    }

    /**
     * Clear.
     */
    public void clear() {
        SpaceMarine[] marines = getMarines();
        for (SpaceMarine marine: marines) {
            remove(findKey(marine));
        }
    }

    @Override
    public String info() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Время инициализации коллекции: ").append(storage.getInitializationTime().toString()).append('\n');
        stringBuilder.append("Количество элементов в коллекции: ").append(storage.size()).append('\n');
        stringBuilder.append("Тип коллекции: ").append(storage.getCollectionClass().getName());
        return stringBuilder.toString();
    }


    @Override
    public void update(int id, SpaceMarine spaceMarine) throws SQLException {
        String key = findKey(findById(id));
        if (handler.getDataBaseManager().update(id, key, spaceMarine)) {

            spaceMarine.setId(id);
            storage.put(key, spaceMarine);
        }
    }

    @Override
    public void save() {
        //toDo
    }

    @Override
    public void removeGreaterThan(SpaceMarine spaceMarine) {
        storage.toValueStream().filter(x -> x.compareTo(spaceMarine) > 0).map(this::findKey).forEach(this::remove);
    }

    public SpaceMarine[] getSortedCollection() {
        return storage.toStream().sorted().toArray(SpaceMarine[]::new);
    }


    @Override
    public void replaceIfGreaterThan(String key, SpaceMarine spaceMarine) {
        storage.toStream().filter(x -> x.getKey() == key)
                .forEach(i -> {
                    if (i.getValue().compareTo(spaceMarine) < 0) {
                        this.remove(i.getKey());
                        this.insert(i.getKey(), spaceMarine);
                        System.out.println("Замена произошла");
                    } else System.out.println("Замена не произошла");
                });
    }

    public SpaceStorage getStorage() {
        return storage;
    }

    /**
     * Remove.
     *
     * @param key the key
     */
    public void remove(String key) {
        String owner = findOwner(key);
        if (owner.equals("-")) {
            handler.writeln("Обьект c таким ключем не существует");
            return;
        }
        String user = getCurrentUser().getName();
        if (handler.getDataBaseManager().removeByKey(key) > 0 && user.equals(owner)) {
            storage.remove(key);
            handler.writeln("Обьект успешно удален");
            return;
        }
        handler.writeln("Это не ваш обьект");
    }

    public void remove(String key, String userName) {
        if (findOwner(key).equals(userName) && handler.getDataBaseManager().removeByKey(key) > 0)
            storage.remove(key);
    }

    @Override
    public boolean removeLowerKey(String key) {
        storage.toKeyStream().filter(x -> x.compareTo(key) < 0).forEach(this::remove);
        return true;
    }

    @Override
    public float sumOfHealth() {
        return (float) storage.toValueStream().map(x -> x.getHealth())
                .mapToDouble(Double::valueOf)
                .sum();

    }

    public List<SpaceMarine> getSorted() {
        return storage.toValueStream().sorted().collect(Collectors.toList());
    }

    @Override
    public long countGreaterThanWeapon(Weapon weapon) {
        return storage.toValueStream().map(x -> x.getWeaponType())
                .filter(x -> x.compareTo(weapon) > 0).count();
    }

    @Override
    public SpaceMarine findById(int id) {
        Optional<SpaceMarine> optional = storage.toValueStream().filter(x -> x.getId() == id).findAny();
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public List<SpaceMarine> filterByCategory(AstartesCategory category) {
        return (storage.toValueStream().filter(x -> x.getCategory() == category).collect(Collectors.toList())).stream().sorted().collect(Collectors.toList());
    }

    /**
     * Find key string.
     *
     * @param spaceMarine the space marine
     * @return the string
     */
    public String findKey(SpaceMarine spaceMarine) {
        for (Map.Entry<String, SpaceMarine> entry : storage.getSpaceMap().entrySet()) {
            if (entry.getValue().equals(spaceMarine)) return entry.getKey();
        }
        return null;

    }

    /**
     * Show.
     */
    public void show() {
        if (storage.toValueStream().count() == 0) {
            getHandler().writeln("Коллекция пуста");
        } else storage.toValueStream().sorted().forEach(getHandler()::writeln);
    }

    public boolean contains(String key) {
        return storage.contains(key);
    }

    public boolean containsId(int id) {
        return storage.containsId(id);
    }

    /**
     * Insert.
     *
     * @param key         the key
     * @param spaceMarine the space marine
     */
    public void insert(String key, SpaceMarine spaceMarine) {
        spaceMarine.setUser(currentUser.getName());
        if (handler.getDataBaseManager().insertSpaceMarine(key, spaceMarine))
            storage.put(key, spaceMarine);
//        storage.update();
    }

    /**
     * Get marines space marine [ ].
     *
     * @return the space marine [ ]
     */
    public SpaceMarine[] getMarines() {
        return storage.toArray();
    }

    public String findOwner(int id) {
        SpaceMarine marine = findById(id);
        if (marine != null) return findById(id).getUserName();
        return "-";
    }

    public String findOwner(String key) {
        SpaceMarine marine = storage.get(key);
        if (marine != null) return marine.getUserName() == null ? "" : storage.get(key).getUserName();
        return "-";
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
