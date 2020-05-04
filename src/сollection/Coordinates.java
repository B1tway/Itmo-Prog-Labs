package сollection;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * The type Coordinates.
 */
public class Coordinates implements Serializable {
    private long x;
    private Long y; //Поле не может быть null

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     */
    public Coordinates(Long x) {
        this.x = x;
    }

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public Coordinates(long x, Long y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.y, y) == 0 &&
                Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        String separator = " : ";
        return x + separator + y;
    }


}
