package сollection;

import network.client.User;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * The type Space marine.
 */
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private String user;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float health; //Поле не может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле может быть null
    private Weapon weaponType; //Поле не может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле не может быть null

    /**
     * Instantiates a new Space marine.
     *
     * @param name        the name
     * @param coordinates the coordinates
     * @param health      the health
     * @param category    the category
     * @param weapon      the weapon
     * @param meleeWeapon the melee weapon
     * @param chapter     the chapter
     */
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
        creationDate = java.time.ZonedDateTime.now();
    }

    /**
     * Instantiates a new Space marine.
     *
     * @param id          the id
     * @param name        the name
     * @param coordinates the coordinates
     * @param health      the health
     * @param category    the category
     * @param weapon      the weapon
     * @param meleeWeapon the melee weapon
     * @param chapter     the chapter
     */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        if (id > 0) this.id = id;
    }

    /**
     * Gets chapter.
     *
     * @return the chapter
     */
    public Chapter getChapter() {
        return chapter;
    }

    /**
     * Sets chapter.
     *
     * @param chapter the chapter
     */
    public void setChapter(Chapter chapter) {
        if (chapter != null) {
            this.chapter = chapter;

        }
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null)
            this.coordinates = coordinates;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public Float getHealth() {
        return health;
    }

    /**
     * Sets health.
     *
     * @param health the health
     */
    public void setHealth(Float health) {
        if (health != null)
            this.health = health;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public AstartesCategory getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(AstartesCategory category) {
        if (category != null)
            this.category = category;
    }

    /**
     * Gets weapon type.
     *
     * @return the weapon type
     */
    public Weapon getWeaponType() {
        return weaponType;
    }

    /**
     * Sets weapon type.
     *
     * @param weaponType the weapon type
     */
    public void setWeaponType(Weapon weaponType) {
        if (weaponType != null)
            this.weaponType = weaponType;
    }

    /**
     * Gets melee weapon.
     *
     * @return the melee weapon
     */
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * Sets melee weapon.
     *
     * @param meleeWeapon the melee weapon
     */
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        if (meleeWeapon != null)
            this.meleeWeapon = meleeWeapon;
    }

    @Override
    public int compareTo(SpaceMarine o) {
        if (health == o.health) {
            if (weaponType.compareTo(o.getWeaponType()) == 1) {
                return 1;
            }
            if (weaponType.compareTo(o.getWeaponType()) == 0) {
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
        return id + separator + name + separator + coordinates.toString() + separator + creationDate.toString() + separator +
                health + separator + category + separator + weaponType + separator + meleeWeapon + separator + chapter.toString();
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getUserName() {
        return user;
    }
}