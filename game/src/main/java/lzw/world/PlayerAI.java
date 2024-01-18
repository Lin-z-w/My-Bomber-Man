package lzw.world;

import java.io.Serializable;
import java.util.List;

import lzw.asciiPanel.AsciiPanel;
import lzw.screen.GlyphDelegate;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayerAI extends CreatureAI implements GlyphDelegate, Serializable {

    int tickDown = 5;
    private final List<String> messages;

    private final CreatureFactory creatureFactory;

    public PlayerAI(Creature creature, List<String> messages, CreatureFactory creatureFactory) {
        super(creature);
        this.messages = messages;
        this.creatureFactory = creatureFactory;
    }

    @Override
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround() && !creature.occupy(x, y)) {
            creature.leaveCurTile();
            creature.enterThisTile(x, y);
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }
    }

    @Override
    public void onNotify(String message) {
        this.messages.add(message);
    }

    @Override
    public void onUpdate() {
        // TODO
    }

    @Override
    public synchronized void attack(Creature another) {
        // TODO
    }

    @Override
    public void put(int direction) {
        switch (direction) {
            case Creature.DIR_UP:
                creatureFactory.newBomb(creature.x(), creature.y()-1);
                break;
            case Creature.DIR_DOWN:
                creatureFactory.newBomb(creature.x(), creature.y()+1);
                break;
            case Creature.DIR_LEFT:
                creatureFactory.newBomb(creature.x()-1, creature.y());
                break;
            case Creature.DIR_RIGHT:
                creatureFactory.newBomb(creature.x()+1, creature.y());
                break;
            default:
                // don't move
                break;
        }
    }

    @Override
    public void printGlyph(AsciiPanel terminal, int offsetX, int offsetY) {
        terminal.write(creature.glyph(), creature.x() - offsetX, creature.y() - offsetY, creature.color());
    }
}
