import utils.UserHandler;

import java.io.IOException;

/**
 * The type Main.
 */
public class Main {
    /**
     * Главный класс.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */

    public static void main(String[] args) throws IOException {
        UserHandler userHandler = new UserHandler();
        userHandler.getCmdManeger().getCommand("load").execute(null);
        while (true) {
            userHandler.nextCommand();
        }
    }
}
