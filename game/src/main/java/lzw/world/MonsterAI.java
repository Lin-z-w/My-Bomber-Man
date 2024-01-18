package lzw.world;

import java.io.Serializable;

public class MonsterAI extends CreatureAI  implements Serializable {

    int tickDown = 10;

    boolean beginAttack = false;
    int direction;

    public MonsterAI(Creature creature) {
        super(creature);
        direction = Creature.DIR_UP;
    }

    public void onUpdate() {
        tickDown--;
        if (tickDown < 0) beginAttack = true;
        chooseDirection();
        // System.out.println("SB");
        switch (direction) {
            case Creature.DIR_UP:
                creature.moveBy(0, -1);
                break;
            case Creature.DIR_DOWN:
                creature.moveBy(0, 1);
                break;
            case Creature.DIR_LEFT:
                creature.moveBy(-1, 0);
                break;
            case Creature.DIR_RIGHT:
                creature.moveBy(1, 0);
                break;
            default:
                // don't move
                break;
        }
    }

    protected void chooseDirection() {
        for (int i = 1; i <= 4; i++) {
            direction %= 4;
            direction++;
            if (creature.attackPlayer(direction)) {
                direction = Creature.DONT_MOVE;
                return;
            }
        }
        if (direction != Creature.DONT_MOVE && creature.nextTile(direction).isGround() && !creature.nextOccupy(direction)) return;
        for (int i = 1; i <= 5; i++) {
            direction %= 4;
            direction++;
            if (creature.nextTile(direction).isGround() && !creature.nextOccupy(direction)) return;
        }
        direction = 0;
    }

    @Override
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.leaveCurTile();
            creature.enterThisTile(x, y);
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }
    }

    @Override
    public void onNotify(String message) {
        throw new UnsupportedOperationException("Unimplemented method 'onNotify'");
    }

    @Override
    public synchronized void attack(Creature another) {
        // TODO
        if (another.isPlayer() && beginAttack) {
            another.modifyHP(-100);
        }
    }

    @Override
    public void put(int direction) {
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }
}
