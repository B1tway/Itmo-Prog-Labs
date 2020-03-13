package utils;

import exceptions.InvalidInputException;
import сollection.*;
import сommands.CommandManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.LinkOption;
import java.text.ParseException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserHandler {
    private CommandManager cmdManeger;
    private Scanner scanner;
    private PrintWriter printWriter;
    private CSVLoader csvLoader;
    private SpaceManager spaceManager;
    private boolean interactive = true;
    private int stackSize = 1000;
    int stackCount = 0;

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public UserHandler() {
        this.scanner = new Scanner(System.in);
        this.printWriter = new PrintWriter(System.out);
        this.cmdManeger = new CommandManager();
        this.csvLoader = new CSVLoader("data.csv");
        cmdManeger.setUserHandler(this);
        this.spaceManager = new SpaceManager();
        spaceManager.setUserHandler(this);
    }

    public boolean nextCommand() throws IOException {
        if (interactive) write(">> ");
        var args = readCommand();
        String cmd = args[0];
        if (cmd.equals("exit")) return false;
        args = Arrays.copyOfRange(args, 1, args.length);
        cmdManeger.executeCommand(cmd, args);
        return true;
    }

    public String[] readCommand() {
        return scanner.nextLine().split(" ");

    }

    public CommandManager getCmdManeger() {
        return cmdManeger;
    }

    public SpaceManager getSpaceManager() {
        return spaceManager;
    }

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

    public AstartesCategory readCategory() {
        AstartesCategory result = null;
        writeln("Введите AstartesCategory");
        String input = null;
        do {
            writeln("DREADNOUGHT, INCEPTOR, TERMINATOR, LIBRARIAN, APOTHECARY");
            input = readLineWithMessage("Введите одну из категорий", false);
            if (isCategory(input)) result = AstartesCategory.valueOf(input);

        } while (!isCategory(input));
        return result;
    }

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

    public Chapter readChapter() {
        writeln("Введите Chapter");
        String name = readLineWithMessage("Введите name", false);
        String parentLegion = readLineWithMessage("Введите parentLegion", true);
        String world = readLineWithMessage("Введите world", true);
        return new Chapter(name, parentLegion, world);
    }

    public float readHealth() {
        float result = 0;
        do {
            result = readFloat("Введите heath ( healt > 0)", false);

        } while (result <= 0);
        return result;
    }

    public Coordinates readCoordinates() {
        writeln("Введите Coordinates");
        long x = readLong("Введите x", false);
        float y = readFloat("Введите y", true);
        return new Coordinates(x, y);
    }

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


    public String readLineWithMessage(String message, boolean nullable) {
        String result = "";
        do {
            if (result == null) {
                writeln("Введите не пустую строку.");
            }
            if (interactive) {
                writeln(message);
            }
            result = scanner.nextLine();
            result = result.isEmpty() ? null : result;
        } while (interactive && !nullable && result == null);
        if (!interactive && result == null) {
            throw new InvalidInputException("Получена пуста строка в поле, которое не может быть null.");
        }
        return result;
    }

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

    public void writeln(String message) {
        write(message + "\n");
    }

    public void write(String message) {
        printWriter.write(message);
        printWriter.flush();
    }

    public boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isLong(String string) {
        try {
            Long.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat(String string) {
        try {
            Float.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isCategory(String string) {
        try {
            AstartesCategory.valueOf(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isWeapon(String string) {
        try {
            Weapon.valueOf(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isMeleeWeapon(String string) {
        try {
            MeleeWeapon.valueOf(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public int getStackCount() {
        return stackCount;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void setStackCount(int stackCount) {
        this.stackCount = stackCount;
    }
    public void upStackCount() {
        stackCount++;
    }
    public void downStackCount() {
        stackCount--;
    }
}
