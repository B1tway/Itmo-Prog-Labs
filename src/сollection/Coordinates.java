package сollection;

import java.util.Objects;

public class Coordinates {
    private Long x; //Поле не может быть null
    private float y;

    public Coordinates(Long x) {
        this.x = x;
    }

    public Coordinates(Long x, float y) {
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
