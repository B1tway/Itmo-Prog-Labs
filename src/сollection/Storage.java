package сollection;

import java.util.Date;

/**
 * The interface Storage.
 *
 * @param <T> the type parameter
 */
public interface Storage<T> {
    /**
     * Put.
     *
     * @param key the key
     * @param obj the obj
     */
    void put(String key, T obj);

    /**
     * Get t.
     *
     * @param key the key
     * @return the t
     */
    T get(String key);

    /**
     * Size int.
     *
     * @return the int
     */
    int size();

    /**
     * Remove.
     *
     * @param key the key
     */
    void remove(String key);

    /**
     * Clear.
     */
    void clear();

    /**
     * Gets initialization time.
     *
     * @return the initialization time
     */
    Date getInitializationTime();

    /**
     * Gets collection class.
     *
     * @return the collection class
     */
    Class<?> getCollectionClass();
}
