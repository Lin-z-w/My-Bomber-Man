package lzw.screen;

import lzw.asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.System.exit;

public class PauseScreen implements Screen{
    BomberGameScreen bomberGameScreen;

    public PauseScreen(BomberGameScreen bomberGameScreen) {
        this.bomberGameScreen = bomberGameScreen;
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("1. Continue Game", 0, 0);
        terminal.write("2. Save Game", 0, 1);
        terminal.write("3. Exit", 0, 2);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        assert (key != null);
        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                return bomberGameScreen;
            case KeyEvent.VK_2:
                ObjectOutputStream out = null;
                FileOutputStream fout = null;
                try {
                    URL url = PauseScreen.class.getResource("/saveGame/game.txt");
                    assert url != null;
                    String filepath = Paths.get(url.toURI()).toFile().getAbsolutePath();
                    fout = new FileOutputStream(filepath);
                    out = new ObjectOutputStream(fout);
                    out.writeObject(bomberGameScreen);
                    return new StartScreen();
                } catch (FileNotFoundException | URISyntaxException e) {
                    throw new RuntimeException(e);
                } finally {
                    assert fout != null;
                    assert out != null;
                    fout.close();
                    out.close();
                }
            case KeyEvent.VK_3:
            case KeyEvent.VK_BACK_SPACE:
                exit(0);
        }
        return this;
    }
}
