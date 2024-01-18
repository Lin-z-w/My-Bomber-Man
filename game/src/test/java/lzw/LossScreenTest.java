package lzw;

import lzw.asciiPanel.AsciiFont;
import lzw.asciiPanel.AsciiPanel;
import lzw.screen.LoseScreen;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LossScreenTest {
    AsciiPanel terminal = new AsciiPanel(30, 30,AsciiFont.CP437_16x16);

    LoseScreen loseScreen = new LoseScreen();
    @Test
    public void testSecUpdate() throws IOException {
        assertEquals(loseScreen, loseScreen.update5hms());
    }

    @Test(expected = AssertionError.class)
    public void testRespondToUserInputAssert() throws IOException {
        loseScreen.respondToUserInput(null);
    }

    @Test
    public void testDisplayOutput() {
        loseScreen.displayOutput(terminal);
    }
}
