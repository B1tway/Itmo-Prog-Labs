package сollection;

import exceptions.ChapterOverflow;

import java.util.Objects;

public class Chapter implements Comparable<Chapter> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private long marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле может быть null

    public Chapter(String name) {
        this.name = name;
    }

    public Chapter(String name, String world) {
        this.name = name;
        this.marinesCount = 0;
        upCount();
        this.world = world;
    }

    public Chapter(String name, String world, long marinesCount) {
        this.name = name;
        this.marinesCount = marinesCount;
        this.world = world;
    }

    public Chapter(String name, String parentLegion, String world) {
        this(name, world);
        this.parentLegion = parentLegion;
    }

    public Chapter(String name, String world, String parentLegion, long marinesCount) {
        this(name, world);
        this.parentLegion = parentLegion;
        this.marinesCount = marinesCount;
    }

    public void upCount() {
        marinesCount++;
        if (marinesCount > 1000) throw new ChapterOverflow("Произошло переполнение легиона");
    }

    public void downCount() {
        marinesCount--;
    }

    public long getCount() {
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
        return name + sepator + parentLegion + sepator + marinesCount + sepator + world;
    }

    public void setMarinesCount(long marinesCount) {
        this.marinesCount = marinesCount;
    }
}
