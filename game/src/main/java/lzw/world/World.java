package lzw.world;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class World  implements Serializable {

    volatile boolean[][] occupies;
    private final Tile[][] tiles;
    private final int width;
    private final int height;
    private final List<Creature> creatures;

    public World(Tile[][] tiles, boolean[][] occu) {
        this.tiles = tiles;
        this.occupies = occu;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public void setTile(int x, int y, Tile tile) {
        if (tile.isGround()) occupies[x][y] = false;
        tiles[x][y] = tile;
    }

    public boolean occupy(int x, int y) {
        return occupies[x][y];
    }

    public void setOccupy(int x, int y, boolean occu) {
        occupies[x][y] = occu;
    }

    public char glyph(int x, int y) {
        return tiles[x][y].glyph();
    }

    public Color color(int x, int y) {
        return tiles[x][y].color();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void dig(int x, int y) {
        if (tile(x, y).isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (!tile(x, y).isGround() || this.creature(x, y) != null);

        occupies[x][y] = true;
        creature.setX(x);
        creature.setY(y);

        this.creatures.add(creature);
    }

    public boolean addAt(Creature creature, int x, int y) {
        if (!tile(x, y).isGround() || occupies[x][y]) return false;
        occupies[x][y] = true;
        creature.setX(x);
        creature.setY(y);
        this.creatures.add(creature);
        return true;
    }

    public Creature creature(int x, int y) {
        for (Creature c : this.creatures) {
            if (c.x() == x && c.y() == y) {
                return c;
            }
        }
        return null;
    }

    public List<Creature> getCreatures() {
        return this.creatures;
    }

    public void remove(Creature target) {
        occupies[target.x()][target.y()] = false;
        this.creatures.remove(target);
    }

    public void update() {
        ArrayList<Creature> toUpdate = new ArrayList<>(this.creatures);

        for (Creature creature : toUpdate) {
            creature.update();
        }
    }
}
