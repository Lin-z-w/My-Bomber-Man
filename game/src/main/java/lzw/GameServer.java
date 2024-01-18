package lzw;

import lzw.screen.MultiPlayerServer;
import javax.swing.*;

public class GameServer extends BomberGame {
    public GameServer() {
        super();
        screen = new MultiPlayerServer();
    }

    public static void main(String[] args) {
        GameServer app = new GameServer();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(false);
    }
}
