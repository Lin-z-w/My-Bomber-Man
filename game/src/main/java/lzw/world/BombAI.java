package lzw.world;

import java.io.Serializable;

public class BombAI extends CreatureAI implements Serializable {

    int tickDown = 6;
    BombAI(Creature creature) {
        super(creature);
    }
    @Override
    public void onEnter(int x, int y, Tile tile) {

    }

    @Override
    public void onUpdate() {
        tickDown--;
        if (tickDown % 2 == 0) {
            creature.setGlyph((char) 9);
        }
        else {
            creature.setGlyph((char) 10);
        }
        if (tickDown == 0) {
            bomb();
        }
        else if (tickDown == -1) {
            removeBomb();
        }
    }

    private void bomb() {
        for (int i = -2; i <= 2; i++) {
            creature.setTile(0, i, Tile.BOMBFLOOR);
            creature.setTile(i,0, Tile.BOMBFLOOR);
            creature.attack(0, i);
            creature.attack(i, 0);
        }
    }

    private void removeBomb() {
        creature.modifyHP(-1000);
        for (int i = -2; i <= 2; i++) {
            creature.setTile(0, i, Tile.FLOOR);
            creature.setTile(i,0, Tile.FLOOR);
        }
    }

    @Override
    public synchronized void attack(Creature another) {
        another.modifyHP(-100);
    }

    @Override
    public void onNotify(String message) {

    }

    @Override
    public void put(int direction) {
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }
}
