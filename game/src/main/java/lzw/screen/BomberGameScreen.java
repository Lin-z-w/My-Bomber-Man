package lzw.screen;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import lzw.asciiPanel.AsciiPanel;
import lzw.world.Creature;
import lzw.world.CreatureFactory;
import lzw.world.World;
import lzw.world.WorldBuilder;

import static java.lang.System.exit;

public class BomberGameScreen implements Screen, Serializable {

    private static final long serialVersionUID = -7783763888965409828L;

    boolean gameLoss = false;

    boolean gameWin = false;
    private static final int WORLD_WIDTH = 30;
    private static final int WORLD_HEIGHT = 30;

    private static final int SCREEN_WIDTH = 30;
    private static final int SCREEN_HEIGHT = 30;

    private static final int USELESS_KEY = KeyEvent.VK_EQUALS;
    protected Map<Integer, Creature> player;

    protected List<String> messages;
    protected List<String> oldMessages;

    protected World world;
    protected CreatureFactory creatureFactory;

    public BomberGameScreen() {
        // super();

        createWorld();
        this.messages = new ArrayList<>();
        this.oldMessages = new ArrayList<>();
        this.player = new HashMap<>();

        creatureFactory = new CreatureFactory(this.world);
        createCreatures(creatureFactory);

        try {
            saveInitGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInitGame() throws IOException {
        ObjectOutputStream out = null;
        FileOutputStream fout = null;
        try {
            URL url = PauseScreen.class.getResource("/gameVideo/game.txt");
            assert url != null;
            String filepath = Paths.get(url.toURI()).toFile().getAbsolutePath();
            fout = new FileOutputStream(filepath);
            out = new ObjectOutputStream(fout);
            out.writeObject(this);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert fout != null;
            assert out != null;
            fout.close();
            out.close();
        }
    }

    protected void createCreatures(CreatureFactory creatureFactory) {
        this.player.put(0, creatureFactory.newPlayer(this.messages));

        for (int i = 0; i < 30; i++) {
            creatureFactory.newMonster();
        }
    }

    protected void createWorld() {
        world = new WorldBuilder(WORLD_WIDTH, WORLD_HEIGHT).makeMaze().build();
    }

    protected Creature getPlayer(int id) {
        return player.get(id);
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal, getScrollX(), getScrollY());
        // Player
        Set<Integer> keys = player.keySet();
        for (Integer i : keys) {
            Creature p = getPlayer(i);
            if (p.isAlive()) {
                ((GlyphDelegate) getPlayer(i).getAI()).printGlyph(terminal, getScrollX(), getScrollY());
            }
        }
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // Show terrain
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
        // Show creatures
        for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + SCREEN_WIDTH && creature.y() >= top
                    && creature.y() < top + SCREEN_HEIGHT) {
                if (creature.getAI() instanceof GlyphDelegate) {
                    ((GlyphDelegate) creature.getAI()).printGlyph(terminal, left, top);
                } else {
                    terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                }
            }
        }
        // Creatures can choose their next action now
        // world.update();
    }

    @Override
    public Screen update5hms() {
        world.update();
        if (getPlayer(0) != null) {
            gameLoss = loss();
        }
        if (gameLoss) {
            return new LoseScreen();
        }
        gameWin = win();
        if (gameWin) {
            return new WinScreen();
        }
        return this;
    }

    boolean append = false;

    @Override
    public Screen update1hms() throws IOException {
        FileOutputStream fout = null;
        try {
            URL url = BomberGameScreen.class.getResource("/gameVideo/userInput.txt");
            assert url != null;
            String filepath = Paths.get(url.toURI()).toFile().getAbsolutePath();
            fout = new FileOutputStream(filepath, append);
            if (!append) append = true;
            fout.write(userInput);
            userInput = USELESS_KEY;
            return this;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert fout != null;
            fout.close();
        }
    }

    public void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = SCREEN_HEIGHT - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.write(messages.get(i), 1, top + i + 1);
        }
        this.oldMessages.addAll(messages);
        messages.clear();
    }

    public int getScrollX() {
        return 0;
    }

    public int getScrollY() {
        return 0;
    }

    int userInput = USELESS_KEY;
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        assert(key != null);
        Creature player = getPlayer(0);
        userInput = key.getKeyCode();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_W:
                player.put(Creature.DIR_UP);
                break;
            case KeyEvent.VK_S:
                player.put(Creature.DIR_DOWN);
                break;
            case KeyEvent.VK_D:
                player.put(Creature.DIR_RIGHT);
                break;
            case KeyEvent.VK_A:
                player.put(Creature.DIR_LEFT);
                break;
            case KeyEvent.VK_ESCAPE:
                return new PauseScreen(this);
            case KeyEvent.VK_BACK_SPACE:
                exit(0);
        }
        return this;
    }

    public boolean win() {
        return world.getCreatures().isEmpty();
    }

    public boolean loss() {
        return !getPlayer(0).isAlive();
    }
}
