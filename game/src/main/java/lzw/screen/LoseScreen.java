package lzw.screen;

import lzw.asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

import static java.lang.System.exit;

public class LoseScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You Loss!", 0, 0);
        terminal.write("1. Go Again.", 0, 1);
        terminal.write("2. Back to Menu.", 0, 2);
        terminal.write("3. Exit.", 0, 3);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        assert(key != null);
        switch (key.getKeyCode()) {
            case KeyEvent.VK_1:
                return new BomberGameScreen();
            case KeyEvent.VK_2:
                return new StartScreen();
            case KeyEvent.VK_3:
            case KeyEvent.VK_BACK_SPACE:
                exit(0);
            default:
                return this;
        }
    }
}
