package lzw;

import lzw.asciiPanel.AsciiFont;
import lzw.asciiPanel.AsciiPanel;
import lzw.screen.BomberGameScreen;
import lzw.screen.PauseScreen;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
public class PauseScreenTest {

    BomberGameScreen bomberGameScreen = new BomberGameScreen();
    PauseScreen startScreen = new PauseScreen(bomberGameScreen);
    AsciiPanel terminal = new AsciiPanel(30, 30, AsciiFont.CP437_16x16);

    @Test
    public void testDisplayOutput() {
        startScreen.displayOutput(terminal);
    }

    @Test(expected = AssertionError.class)
    public void testRespondToUserInputAssert() throws IOException {
        startScreen.respondToUserInput(null);
    }

    @Test
    public void testSecUpdate() throws IOException {
        assertEquals(startScreen, startScreen.update5hms());
    }
}
