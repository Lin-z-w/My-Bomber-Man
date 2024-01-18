package lzw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import lzw.asciiPanel.AsciiFont;
import lzw.asciiPanel.AsciiPanel;
import lzw.screen.Screen;
import lzw.screen.StartScreen;

public class BomberGame extends JFrame implements KeyListener {

    private final AsciiPanel terminal;
    protected Screen screen;

    class KeyListenerThread implements Runnable {
        @Override
        public void run() {
            updateByKeyListener();
        }
    }

    class MonsterMoveThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                    screen = screen.update5hms();
                    repaint();
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class VideoThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                    screen = screen.update1hms();
                    repaint();
                } catch (InterruptedException e) {
                    System.out.println("Error");
                } catch (IOException | AWTException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public BomberGame() {
        super();
        terminal = new AsciiPanel(30, 30, AsciiFont.CP437_16x16);
        add(terminal);
        pack();
        screen = new StartScreen();

        Runnable t1 = new KeyListenerThread();
        Runnable t2 = new MonsterMoveThread();
        Runnable t3 = new VideoThread();
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
    }

    public void updateByKeyListener() {
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        try {
            screen = screen.respondToUserInput(e);
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        BomberGame app = new BomberGame();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
