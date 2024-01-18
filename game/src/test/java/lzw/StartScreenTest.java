package lzw;

import lzw.asciiPanel.AsciiFont;
import lzw.asciiPanel.AsciiPanel;
import lzw.screen.StartScreen;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class StartScreenTest {
    StartScreen startScreen = new StartScreen();
    AsciiPanel terminal = new AsciiPanel(30, 30, AsciiFont.CP437_16x16);

    @Test
    public void testSecUpdate() throws IOException {
        assertEquals(startScreen, startScreen.update5hms());
    }

    @Test(expected = AssertionError.class)
    public void testRespondToUserInputAssert() throws IOException, URISyntaxException {
        startScreen.respondToUserInput(null);
    }

    @Test
    public void testDisplayOutput() {
        startScreen.displayOutput(terminal);
    }
}
