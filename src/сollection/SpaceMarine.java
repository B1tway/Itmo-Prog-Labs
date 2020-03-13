package сollection;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class SpaceMarine implements Comparable<SpaceMarine> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float health; //Поле не может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле не может быть null
    private Weapon weaponType; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле может быть null

    public SpaceMarine(String name, Coordinates coordinates, Float health, AstartesCategory category, Weapon weapon, MeleeWeapon meleeWeapon,
                       Chapter chapter) {
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        assert health > 0;
        this.category = category;
        this.weaponType = weapon;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
        this.chapter.upCount();
        creationDate = new Date();
    }

    public SpaceMarine(int id,
                       String name,
                       Coordinates coordinates,
                       Float health,
                       AstartesCategory category,
                       Weapon weapon,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) {
        this(name, coordinates, health, category, weapon, meleeWeapon, chapter);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) this.id = id;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        if (chapter != null) {
            this.chapter.downCount();
            this.chapter = chapter;
            this.chapter.upCount();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null)
            this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Float getHealth() {
        return health;
    }

    public void setHealth(Float health) {
        if (health != null)
            this.health = health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setCategory(AstartesCategory category) {
        if (category != null)
            this.category = category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Weapon weaponType) {
        if (weaponType != null)
            this.weaponType = weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        if (meleeWeapon != null)
            this.meleeWeapon = meleeWeapon;
    }

    @Override
    public int compareTo(SpaceMarine o) {
        if (health == o.health) {
            if (chapter.compareTo(o.chapter) == 1) {
                return 1;
            }
            if (chapter.compareTo(o.chapter) == 0) {
                return 0;
            }
            return -1;
        }
        if (health >= o.health) return 1;
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return id == that.id &&
                that.name.equals(name) &&
                that.coordinates.equals(coordinates) &&
                that.creationDate.equals(creationDate) &&
                Objects.equals(health, that.health) &&
                category == that.category &&
                weaponType == that.weaponType &&
                meleeWeapon == that.meleeWeapon &&
                that.chapter.equals(chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, health, category, weaponType, meleeWeapon, chapter);
    }

    @Override
    public String toString() {
        String separator = " ; ";
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return id + separator + name + separator + coordinates.toString() + separator + formatter.format(creationDate) + separator +
                health + separator + category + separator + weaponType + separator + meleeWeapon + separator + chapter.toString();
    }

}