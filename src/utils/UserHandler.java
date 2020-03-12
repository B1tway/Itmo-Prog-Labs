package utils;

import exceptions.IncorrectInputException;
import сollection.Chapter;
import сollection.Coordinates;
import сollection.SpaceManager;
import сollection.SpaceMarine;
import сommands.Command;
import сommands.CommandManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserHandler {
    private CommandManager cmdManeger;
    private Scanner scanner;
    private PrintWriter printWriter;
    private SpaceManager spaceManager;

    public UserHandler() {
        this.scanner = new Scanner(System.in);
        this.printWriter = new PrintWriter(System.out);
        this.cmdManeger = new CommandManager();
        cmdManeger.setUserHandler(this);
        this.spaceManager = new SpaceManager();
        spaceManager.setUserHandler(this);
    }

    public boolean nextCommand() throws IOException {
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

//    public SpaceMarine readSpaceMarine() {
//
//        System.out.println("Введите имя:");
//        String name = scanner.next();
//
//    }

    public Coordinates readCoordinates() {
        System.out.println("Введите координаты, как два целых числа x и y: ");
        long x, y;
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.println("Некорректный ввод x:");
        }
        x = scanner.nextLong();
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.println("Некорректный ввод y:");
        }
        y = scanner.nextLong();
        return new Coordinates(x, y);
    }

//    public Chapter readChapter() {
//        System.out.println("Введите Легион:");
//        String name;
//        while (!scanner.hasNext()) {
//            System.out.println("Введите name");
//            name = scanner.next();
//        }
//        while (!scanner.hasNext()) {
//            System.out.println("Введите parentLegion (может быть пустым)");
//            name = scanner.next();
//        }
//
//
//    }
}
