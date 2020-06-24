package network.server;

import network.client.User;
import utils.Handler;
import сommands.Command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class CliThread extends Thread {
    Handler handler;

    public CliThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while (true) {
            handler.setInteractive(false);
            PrintWriter old = handler.getPrintWriter();
            handler.setPrintWriter(new PrintWriter(System.out));
            try {
                Command cmd = handler.nextCommand();
                cmd.setUser(new User("server", ""));
                cmd.setCmdManager(handler.getCmdManeger());
                cmd.execute(cmd.getArgs());
            } catch (IndexOutOfBoundsException exp) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            handler.setInteractive(true);
            handler.setPrintWriter(old);
        }

    }
}
