package utils;

import exceptions.InvalidInputException;
import сollection.*;
import сommands.Command;
import сommands.CommandManager;
import сommands.EmptyCommand;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * The type User handler.
 */
public class Handler {
    /**
     * The Stack count.
     */
    int stackCount = 0;
    private CommandManager cmdManeger;
    private Scanner scanner;
    private PrintWriter printWriter;
    private CSVLoader csvLoader;
    private SpaceManager spaceManager;
    private boolean interactive = true;
    private int stackSize = 1000;
    private Set<String> files;
    private boolean isEmptyInput = false;

    /**
     * Instantiates a new User handler.
     */
    public Handler() {
        this.scanner = new Scanner(System.in);
        this.printWriter = new PrintWriter(System.out);
        this.cmdManeger = new CommandManager();
        this.csvLoader = new CSVLoader("data.csv");
        cmdManeger.setHandler(this);
        this.spaceManager = new SpaceManager();
        spaceManager.setHandler(this);
        this.files = new HashSet<>();
    }

    /**
     * Sets interactive.
     *
     * @param interactive the interactive
     */
    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public boolean isEmptyInput() {
        return isEmptyInput;
    }

    /**
     * Next command boolean.
     *
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean next() throws IOException {
        if (interactive) write(">> ");
        String[] args = readCommand();
        if (args.length == 0) return true;
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        return cmdManeger.executeCommand(cmd, args);

    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public Command nextCommand() throws IOException {
        if (interactive) write(">> ");
        String[] args = readCommand();
        if (args.length == 0) return new EmptyCommand();
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        return cmdManeger.getCommand(cmd, args);
    }

    public Command nextCommand(long time) throws IOException {
        String[] args = readCommand(time);
        if (args.length == 0) return new EmptyCommand();
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        return cmdManeger.getCommand(cmd, args);
    }

    public Set<String> getFiles() {
        return files;
    }


    /**
     * Read command string [ ].
     *
     * @return the string [ ]
     */
    public String[] readCommand() {
        List<String> list = new ArrayList<>();
        String str = null;
        StringTokenizer st = null;
        try {
            str = scanner.nextLine();
            st = new StringTokenizer(str);

        } catch (NoSuchElementException exp) {
            System.out.println("Bye bye");
            System.exit(0);

        }

        while (st.hasMoreTokens()) list.add(st.nextToken());
        String[] args = list.toArray(new String[list.size()]);
        return args;

    }

    public String[] readCommand(long time) {
        isEmptyInput = false;
        List<String> list = new ArrayList<>();
        String str = null;
        StringTokenizer st = null;
        long end = System.currentTimeMillis() + time;
        try {
            while (System.currentTimeMillis() < end && str == null) str = scanner.nextLine();
            if (str == null) {
                str = "";
                isEmptyInput = true;
            }
            st = new StringTokenizer(str);

        } catch (NoSuchElementException exp) {
            System.out.println("Bye bye");
            System.exit(0);

        }

        while (st.hasMoreTokens()) list.add(st.nextToken());
        String[] args = list.toArray(new String[list.size()]);
        return args;

    }

    /**
     * Gets cmd maneger.
     *
     * @return the cmd maneger
     */
    public CommandManager getCmdManeger() {
        return cmdManeger;
    }

    /**
     * Gets space manager.
     *
     * @return the space manager
     */
    public SpaceManager getSpaceManager() {
        return spaceManager;
    }

    /**
     * Read space marine space marine.
     *
     * @return the space marine
     */
    public SpaceMarine readSpaceMarine() {
        writeln("Введите SpaceMarine");
        String name = readLineWithMessage("Введите name", false);
        Coordinates coordinates = readCoordinates();
        float healt = readHealth();
        AstartesCategory category = readCategory();
        Weapon weapon = readWeapon();
        MeleeWeapon meleeWeapon = readMeleeWeapon();
        Chapter chapter = readChapter();
        return new SpaceMarine(name, coordinates, healt, category, weapon, meleeWeapon, chapter);

    }

    /**
     * Read category astartes category.
     *
     * @return the astartes category
     */
    public AstartesCategory readCategory() {
        AstartesCategory result = null;
        writeln("Введите AstartesCategory");
        String input = null;
        do {
            writeln("DREADNOUGHT, INCEPTOR, TERMINATOR, LIBRARIAN, APOTHECARY");
            input = readLineWithMessage("Введите одну из категорий", true);
            if (isCategory(input)) result = AstartesCategory.valueOf(input);

        } while (!isCategory(input) && !(input == null));
        return result;
    }

    /**
     * Read weapon weapon.
     *
     * @return the weapon
     */
    public Weapon readWeapon() {
        Weapon result = null;
        writeln("Введите Weapon");
        String input = null;
        do {
            writeln("COMBI_PLASMA_GUN, GRAV_GUN, GRENADE_LAUNCHER, MISSILE_LAUNCHER");
            input = readLineWithMessage("Введите одно из оружий", false);
            if (isWeapon(input)) result = Weapon.valueOf(input);

        } while (!isWeapon(input));
        return result;
    }

    /**
     * Read melee weapon melee weapon.
     *
     * @return the melee weapon
     */
    public MeleeWeapon readMeleeWeapon() {
        MeleeWeapon result = null;
        writeln("Введите MeleeWeapon");
        String input = null;
        do {
            writeln("CHAIN_AXE, MANREAPER, LIGHTING_CLAW, POWER_BLADE;");
            input = readLineWithMessage("Введите одно из оружий", false);
            if (isMeleeWeapon(input)) result = MeleeWeapon.valueOf(input);

        } while (!isMeleeWeapon(input));
        return result;
    }

    /**
     * Read chapter chapter.
     *
     * @return the chapter
     */
    public Chapter readChapter() {
        writeln("Введите Chapter");
        String name = readLineWithMessage("Введите name", false);
        String parentLegion = readLineWithMessage("Введите parentLegion", true);
        String world = readLineWithMessage("Введите world", true);
        Integer marineCount = -1;
        while (!(marineCount < 1001 && marineCount > 0)) {
            String s = readLineWithMessage("Введите marinesCount от 1 до 1000", true);
            if (s == null) {
                marineCount = null;
                break;
            }
            if (!isInteger(s)) continue;
            marineCount = Integer.parseInt(s);
        }
        return new Chapter(name, parentLegion, world, marineCount);
    }

    /**
     * Read health float.
     *
     * @return the float
     */
    public float readHealth() {
        float result = 0;
        do {
            result = readFloat("Введите heath ( healt > 0)", false);

        } while (result <= 0);
        return result;
    }

    /**
     * Read coordinates coordinates.
     *
     * @return the coordinates
     */
    public Coordinates readCoordinates() {
        writeln("Введите Coordinates");
        long x = readLong("Введите x", false);
        Long y = readLong("Введите y", false);
        return new Coordinates(x, y);
    }

    /**
     * Read long long.
     *
     * @param message  the message
     * @param nullable the nullable
     * @return the long
     */
    public long readLong(String message, boolean nullable) {
        long result = 0;
        String input = null;
        do {
            input = readLineWithMessage(message, nullable);
            if (input == null && nullable) return result;
            if (isLong(input)) result = Long.parseLong(input);

        } while (!isLong(input));
        return result;
    }

    /**
     * Read float float.
     *
     * @param message  the message
     * @param nullable the nullable
     * @return the float
     */
    public float readFloat(String message, boolean nullable) {
        float result = 0;
        String input = null;
        do {
            input = readLineWithMessage(message, nullable);
            if (input == null && nullable) return result;
            if (isFloat(input)) result = Float.parseFloat(input);

        } while (!isFloat(input));
        return result;
    }


    /**
     * Read line with message string.
     *
     * @param message  the message
     * @param nullable the nullable
     * @return the string
     */
    public String readLineWithMessage(String message, boolean nullable) {
        String result = "";
        do {
            if (result == null) {
                writeln("Введите не пустую строку.");
            }
            if (interactive) {
                writeln(message);
            }
            try {
                result = scanner.nextLine();

            } catch (NoSuchElementException exp) {
                System.out.println("Bye bye");
                System.exit(0);

            }
            result = result.isEmpty() ? null : result;
        } while (interactive && !nullable && result == null);
        if (!interactive && result == null) {
            throw new InvalidInputException("Получена пуста строка в поле, которое не может быть null.");
        }
        return result;
    }

    /**
     * Read with message string.
     *
     * @param message  the message
     * @param nullable the nullable
     * @return the string
     */
    public String readWithMessage(String message, boolean nullable) {
        String result = "";
        do {
            if (result == null) {
                writeln("Введите не пустую строку.");
            }
            if (interactive) {
                writeln(message);
            }
            result = scanner.next();
            result = result.isEmpty() ? null : result;
        } while (interactive && !nullable && result == null);
        if (!interactive && result == null) {
            throw new InvalidInputException("Получена пуста строка в поле, которое не может быть null.");
        }
        return result;
    }

    /**
     * Writeln.
     *
     * @param message the message
     */
    public void writeln(String message) {
        write(message + "\n");
    }

    /**
     * Write.
     *
     * @param message the message
     */
    public void write(String message) {
        printWriter.write(message);
        printWriter.flush();
    }

    public int readInt(String message, boolean nullable) {
        int result = 0;
        String input = null;
        do {
            input = readLineWithMessage(message, nullable);
            if (input == null && nullable) return result;
            if (isInteger(input)) result = Integer.parseInt(input);

        } while (!isFloat(input));
        return result;
    }

    /**
     * Is integer boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Is long boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public boolean isLong(String string) {
        try {
            Long.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Is float boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public boolean isFloat(String string) {
        try {
            Float.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Is category boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public boolean isCategory(String string) {
        try {
            AstartesCategory.valueOf(string);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

    /**
     * Is weapon boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public boolean isWeapon(String string) {
        try {
            Weapon.valueOf(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Is melee weapon boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public boolean isMeleeWeapon(String string) {
        try {
            MeleeWeapon.valueOf(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Gets scanner.
     *
     * @return the scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Sets scanner.
     *
     * @param scanner the scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gets stack count.
     *
     * @return the stack count
     */
    public int getStackCount() {
        return stackCount;
    }

    /**
     * Sets stack count.
     *
     * @param stackCount the stack count
     */
    public void setStackCount(int stackCount) {
        this.stackCount = stackCount;
    }

    /**
     * Gets stack size.
     *
     * @return the stack size
     */
    public int getStackSize() {
        return stackSize;
    }

    /**
     * Up stack count.
     */
    public void upStackCount() {
        stackCount++;
    }

    /**
     * Down stack count.
     */
    public void downStackCount() {
        stackCount--;
    }

    public void writeln(Object object) {
        writeln(object.toString());
    }
}
