package сollection;

import java.util.*;
import java.util.stream.Stream;

/**
 * The type Space storage.
 */
public class SpaceStorage implements Storage<SpaceMarine> {
    private TreeMap<String, SpaceMarine> spaceMap;
    private HashSet<Integer> ids;
    private Date date;

    /**
     * Instantiates a new Space storage.
     */
    public SpaceStorage() {
        date = new Date();
        spaceMap = new TreeMap<>();
        ids = new HashSet<>();

    }

    /**
     * Gets space map.
     *
     * @return the space map
     */
    public TreeMap<String, SpaceMarine> getSpaceMap() {
        return spaceMap;
    }

    /**
     * Sets space map.
     *
     * @param spaceMap the space map
     */
    public void setSpaceMap(TreeMap<String, SpaceMarine> spaceMap) {
        this.spaceMap = spaceMap;
    }

    @Override
    public void put(String key, SpaceMarine obj) {
        if (obj.getId() == 0) obj.setId(generateId());
        spaceMap.put(key, obj);
        update();
    }

    @Override
    public SpaceMarine get(String key) {
        return spaceMap.get(key);
    }

    @Override
    public int size() {
        return spaceMap.size();
    }

    @Override
    public void remove(String key) {
        spaceMap.remove(key);
        update();
    }


    @Override
    public void clear() {
        spaceMap.clear();
        ids.clear();

    }

    /**
     * To array space marine [ ].
     *
     * @return the space marine [ ]
     */
    public SpaceMarine[] toArray() {
        Collection<SpaceMarine> collection = spaceMap.values();
        SpaceMarine[] marines = collection.toArray(new SpaceMarine[collection.size()]);
        return marines;
    }


    @Override
    public Date getInitializationTime() {
        return date;
    }

    @Override
    public Class<?> getCollectionClass() {
        return spaceMap.getClass();
    }

    /**
     * Contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean contains(String key) {
        return spaceMap.containsKey(key);
    }


    /**
     * Gets chapters.
     *
     * @return the chapters
     */


    /**
     * Update.
     */
    public void update() {
        if (size() == 0) clear();
        this.ids = new HashSet<>();
        for (SpaceMarine marine : toArray()) {
            int id = marine.getId();
            if (id == 0) {
                id = generateId();
                marine.setId(id);
            }
            ids.add(id);

        }
        this.ids = ids;
    }

    private int generateId() {
        Random random = new Random();
        int id = random.nextInt();
        while (ids.contains(id) && id <= 0) id = (random.nextInt() % (Integer.MAX_VALUE - 1)) + 1;
        return id;
    }

    /**
     * Contains id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean containsId(int id) {
        return ids.contains(id);
    }

    /**
     * Find chapter chapter.
     *
     * @param chapter the chapter
     * @return the chapter
     */
    public Stream<SpaceMarine> toValueStream() {
        return spaceMap.values().stream();
    }

    public Stream<Map.Entry<String, SpaceMarine>> toStream() {
        return spaceMap.entrySet().stream();
    }

    public Stream<String> toKeyStream() {
        return spaceMap.keySet().stream();
    }


}
