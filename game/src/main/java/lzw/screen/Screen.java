package lzw.screen;

import lzw.asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Screen {

    void displayOutput(AsciiPanel terminal);

    default Screen update5hms() throws IOException {
        return this;
    }

    default Screen update1hms() throws IOException, AWTException {
        return this;
    }

    Screen respondToUserInput(KeyEvent key) throws IOException, URISyntaxException;
}
