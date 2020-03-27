package сollection;

import exceptions.ChapterOverflow;

import java.util.Objects;
import java.util.function.IntBinaryOperator;

/**
 * The type Chapter.
 */
public class Chapter implements Comparable<Chapter> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private Integer  marinesCount; //Поле может быть null
    private String world; //Поле может быть null

    public Chapter(String name, String parentLegion, String world, Integer marinesCount) {
        this.name = name;
        this.parentLegion = parentLegion;
        this.world = world;
        this.marinesCount = marinesCount;
    }




    /**
     * Gets count.
     *
     * @return the count
     */
    public Integer getCount() {
        return marinesCount;
    }

    @Override
    public int compareTo(Chapter o) {
        if (marinesCount == o.marinesCount) return 0;
        if (marinesCount > o.marinesCount) return 1;
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(name, chapter.name) &&
                Objects.equals(parentLegion, chapter.parentLegion) &&
                Objects.equals(world, chapter.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentLegion, marinesCount, world);
    }

    @Override
    public String toString() {
        String sepator = ":";
        String cnt = ((marinesCount == null || marinesCount == 0 ))? null : marinesCount.toString();
        return name + sepator + parentLegion + sepator + world + sepator + cnt;
    }


}
