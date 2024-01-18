package lzw.screen;

import lzw.asciiPanel.AsciiPanel;
import lzw.world.Creature;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class MultiPlayerServer extends BomberGameScreen implements Serializable {

    public int newID = 1;
    public MultiPlayerServer() {
        super();
        getPlayer(0).modifyHP(-100);
        new Thread(new ServerReactorThread(this)).start();
    }

    public void displayOutput(AsciiPanel terminal) {
        super.displayOutput(terminal);
    }

    public void newClient() {
        newID++;
        player.put(newID, creatureFactory.newPlayer(this.messages));
    }

    public Screen update5hms() {
        world.update();
        return this;
    }

    public void respondToClientInput(String s) {
        if (s.contains("r")) return;
        String[] spString = s.split("\\s+");
        int keyCode = Integer.parseInt(spString[0]);
        System.out.println(keyCode);
        int id = Integer.parseInt(spString[1]);
        System.out.println(id);
        Creature clientPlayer = getPlayer(id);
        if (clientPlayer == null) return;
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                clientPlayer.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                clientPlayer.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                clientPlayer.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                clientPlayer.moveBy(0, 1);
                break;
            case KeyEvent.VK_W:
                clientPlayer.put(Creature.DIR_UP);
                break;
            case KeyEvent.VK_S:
                clientPlayer.put(Creature.DIR_DOWN);
                break;
            case KeyEvent.VK_D:
                clientPlayer.put(Creature.DIR_RIGHT);
                break;
            case KeyEvent.VK_A:
                clientPlayer.put(Creature.DIR_LEFT);
                break;
        }
    }
}

