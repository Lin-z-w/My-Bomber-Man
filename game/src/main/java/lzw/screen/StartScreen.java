package lzw.screen;

import lzw.asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;

import static java.lang.System.exit;

public class StartScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("1. Start a Single-Player Game", 0, 0);
        terminal.write("2. Start a Multi-Player Game", 0, 1);
        terminal.write("3. Continue Last Game", 0, 2);
        terminal.write("4. Video of Last Game", 0, 3);
        terminal.write("5. Exit", 0, 4);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        assert(key != null);
        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                return new BomberGameScreen();
            case KeyEvent.VK_2:
                return new MultiPlayerClientScreen();
            case KeyEvent.VK_3:
                ObjectInputStream in = null;
                FileInputStream fin = null;
                try {
                    URL url = StartScreen.class.getResource("/saveGame/game.txt");
                    assert url != null;
                    String filename = url.getPath();
                    fin = new FileInputStream(filename);
                    in = new ObjectInputStream(fin);
                    Object bomberGameScreen = in.readObject();
                    assert bomberGameScreen instanceof BomberGameScreen;
                    return (BomberGameScreen) bomberGameScreen;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    assert fin != null;
                    assert in != null;
                    fin.close();
                    in.close();
                }
            case KeyEvent.VK_4:
                return new VideoScreen();
            case KeyEvent.VK_5:
            case KeyEvent.VK_BACK_SPACE:
                exit(0);
            default:
                return this;
        }
    }
}
