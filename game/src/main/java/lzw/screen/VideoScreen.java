package lzw.screen;

import lzw.asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VideoScreen implements Screen{

    int cnt = 0;

    List<Integer> userInput = new ArrayList<>();

    BomberGameScreen bomberGameScreen;

    VideoScreen() throws IOException {
        ObjectInputStream in = null;
        FileInputStream fin = null;
        try {
            URL url = VideoScreen.class.getResource("/gameVideo/game.txt");
            assert url != null;
            String filename = url.getPath();
            fin = new FileInputStream(filename);
            in = new ObjectInputStream(fin);
            Object bomberGameScreen = in.readObject();
            assert bomberGameScreen instanceof BomberGameScreen;
            this.bomberGameScreen = (BomberGameScreen) bomberGameScreen;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        fin.close();
        in.close();

        URL url = VideoScreen.class.getResource("/gameVideo/userInput.txt");
        assert url != null;
        String filename = url.getPath();
        fin = new FileInputStream(filename);
        while (true) {
            try {
                int num = fin.read();
                // System.out.println(num);
                if (num == -1) break;
                userInput.add(num);
            } catch (Exception e) {
                System.out.println("userInput load error");
            }
        }
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        bomberGameScreen.displayOutput(terminal);
    }

    @Override
    public Screen update1hms() throws AWTException {
        if (cnt == userInput.size()) return new StartScreen();
        Robot robot = new Robot();
        robot.keyPress(userInput.get(cnt));
        robot.keyRelease(userInput.get(cnt));
        cnt++;
        return this;
    }

    @Override
    public Screen update5hms() {
        bomberGameScreen.update5hms();
        return this;
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        bomberGameScreen.respondToUserInput(key);
        return this;
    }
}
