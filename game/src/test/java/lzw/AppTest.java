package lzw;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.BindException;

public class AppTest {


    @Test
    public void testBomberGame() throws AWTException {
        BomberGame app = new BomberGame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyPress(KeyEvent.VK_2);
        robot.keyPress(KeyEvent.VK_2);
    }

    @Test
    public void testGameServer() throws AWTException {
        GameServer gameServer = new GameServer();
        gameServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameServer.setVisible(false);

        BomberGame app = new BomberGame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyPress(KeyEvent.VK_2);
        robot.keyPress(KeyEvent.VK_2);
    }
}
