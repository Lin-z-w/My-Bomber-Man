package lzw;

import lzw.asciiPanel.AsciiFont;
import lzw.asciiPanel.AsciiPanel;
import lzw.screen.WinScreen;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WinScreenTest {
    AsciiPanel terminal = new AsciiPanel(30, 30, AsciiFont.CP437_16x16);

    WinScreen winScreen = new WinScreen();

    @Test
    public void testSecUpdate() throws IOException {
        assertEquals(winScreen, winScreen.update5hms());
    }

    @Test(expected = AssertionError.class)
    public void testRespondToUserInputAssert() throws IOException {
        winScreen.respondToUserInput(null);
    }

    @Test
    public void testDisplayOutput() {
        winScreen.displayOutput(terminal);
    }
}
