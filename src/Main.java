import utils.UserHandler;

import java.io.*;
import java.nio.CharBuffer;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The type Main.
 */
public class Main {
    public static String delEdges(String s) {
        return s.substring(1,s.length()-1);
    }
    /**
     * Главный класс.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */

    public static void main(String[] args) throws IOException {

        UserHandler userHandler = new UserHandler();
        userHandler.getSpaceManager().getSortedCollection();
        userHandler.getCmdManeger().getCommand("load").execute(null);
        while (true) {
            userHandler.nextCommand();
        }
    }
}
