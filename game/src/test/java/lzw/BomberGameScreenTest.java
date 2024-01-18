package lzw;


import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import lzw.asciiPanel.AsciiFont;
import lzw.asciiPanel.AsciiPanel;
import lzw.screen.BomberGameScreen;

public class BomberGameScreenTest {

    BomberGameScreen bbgScreen = new BomberGameScreen();

    AsciiPanel terminal = new AsciiPanel(30, 30, AsciiFont.CP437_16x16);



    @Test(expected = AssertionError.class)
    public void testRespondToUserInputAssert() {
        bbgScreen.respondToUserInput(null);
    }

    @Test
    public void testGetScrollX_Y() {
        assertEquals(0, bbgScreen.getScrollX());
        assertEquals(0, bbgScreen.getScrollY());
    }

    @Test
    public void testDisplayOutput() {
        bbgScreen.displayOutput(terminal);
    }

    @Test
    public void testSecUpdate() {
        assertEquals(bbgScreen, bbgScreen.update5hms());
    }

    @Test
    public void testDisplayMessage() {
        bbgScreen.displayMessages(terminal, new ArrayList<String>());
    }
}
