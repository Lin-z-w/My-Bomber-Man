package lzw.world;

import java.awt.Color;
import java.io.Serializable;

public class Creature implements Serializable {

    public static final int DONT_MOVE = 0;
    public static final int DIR_UP = 1;
    public static final int DIR_DOWN = 2;
    public static final int DIR_LEFT = 3;
    public static final int DIR_RIGHT = 4;

    private final World world;

    private final boolean isPlayer;

    public boolean isPlayer() {
        return isPlayer;
    }

    boolean alive = true;
    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }


    private int y;

    public void setY(int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }

    public void setGlyph(char glyph) {
        this.glyph = glyph;
    }
    private final Color color;

    public Color color() {
        return this.color;
    }

    private CreatureAI ai;

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    public CreatureAI getAI(){
        return this.ai;
    }

    private final int maxHP;

//    public int maxHP() {
//        return this.maxHP;
//    }

    private int hp;

    public int hp() {
        return this.hp;
    }

    public void modifyHP(int amount) {
        this.hp += amount;

        if (this.hp < 1) {
            if (this.isPlayer) {
                alive = false;
                // world.remove(this);
            }
            world.remove(this);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    private final int attackValue;

    public int attackValue() {
        return this.attackValue;
    }

    public void attack(int mx, int my) {
        if (mx+x <= 0 || mx+x >= world.width()-1 || my+y <= 0 || my+y >= world.height()-1) return;
        Creature other = world.creature(x + mx, y + my);
        if (other != null) {
            ai.attack(other);
        }
    }
    public void put(int direction) {
        ai.put(direction);
    }

    private final int defenseValue;

    public int defenseValue() {
        return this.defenseValue;
    }

    public boolean canSee(int wx, int wy) {
        return ai.canSee(wx, wy);
    }

    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    public boolean occupy(int wx, int wy) {
        return world.occupy(wx, wy);
    }

    public void setOccupy(int wx, int wy, boolean occu) {
        world.setOccupy(wx, wy, occu);
    }

    public void leaveCurTile() {
        world.setOccupy(x, y, false);
    }

    public void enterThisTile(int wx, int wy) {
        x = wx;
        y = wy;
        world.setOccupy(x, y, true);
    }
    public Tile thisTile() { return tile(x, y); }
    public Tile nextTile(int direction) {
        switch (direction) {
            case Creature.DONT_MOVE:
                return tile(x,y);
            case Creature.DIR_UP:
                return tile(x, y-1);
            case Creature.DIR_DOWN:
                return tile(x, y+1);
            case Creature.DIR_LEFT:
                return tile(x-1, y);
            case Creature.DIR_RIGHT:
                return tile(x+1, y);
            default:
                assert(false);
                return null;
        }
    }

    public boolean nextOccupy(int direction) {
        switch (direction) {
            case Creature.DONT_MOVE:
                return occupy(x,y);
            case Creature.DIR_UP:
                return occupy(x, y-1);
            case Creature.DIR_DOWN:
                return occupy(x, y+1);
            case Creature.DIR_LEFT:
                return occupy(x-1, y);
            case Creature.DIR_RIGHT:
                return occupy(x+1, y);
            default:
                assert(false);
                return false;
        }
    }

    public boolean attackPlayer(int direction) {
        Creature c;
        switch (direction) {
            case Creature.DONT_MOVE:
                c = world.creature(x, y);
                break;
            case Creature.DIR_UP:
                c = world.creature(x, y-1);
                break;
            case Creature.DIR_DOWN:
                c = world.creature(x, y+1);
                break;
            case Creature.DIR_LEFT:
                c = world.creature(x-1, y);
                break;
            case Creature.DIR_RIGHT:
                c = world.creature(x+1, y);
                break;
            default:
                assert(false);
                return false;
        }
        if (c != null && c.isPlayer()) {
            ai.attack(c);
            return true;
        }
        return false;
    }

    public void setTile(int mx, int my, Tile tile) {
        if (mx+x <= 0 || mx+x >= world.width()-1 || my+y <= 0 || my+y >= world.height()-1) return;
        world.setTile(mx+x, my+y, tile);
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
    }

    public void update() {
        this.ai.onUpdate();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public Creature(World world, char glyph, Color color, int maxHP, int attack, int defense, int visionRadius, boolean isPlayer) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.isPlayer = isPlayer;
    }
}
