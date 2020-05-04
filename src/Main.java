import utils.Handler;
import сommands.Command;

import java.io.*;

/**
 * The type Main.
 */
public class Main {
    public static String delEdges(String s) {
        return s.substring(1, s.length() - 1);
    }

    /**
     * Главный класс.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Handler handler = new Handler();
        handler.getSpaceManager().getSortedCollection();
        handler.getCmdManeger().getCommand("load").execute(null);
        while (true) {
            handler.next();
        }

    }
}
