import utils.CSVLoader;
import utils.UserHandler;
import сollection.*;
import сommands.Command;
import сommands.HelpCommand;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) throws IOException {
        UserHandler userHandler = new UserHandler();
        while (true) {
            if (!userHandler.nextCommand()) break;
        }


    }
}
