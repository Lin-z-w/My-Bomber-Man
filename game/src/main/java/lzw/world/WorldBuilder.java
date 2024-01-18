package lzw.world;

import java.io.Serializable;

public class WorldBuilder {

    private final int width;
    private final int height;
    private final Tile[][] tiles;

    private final boolean[][] occupys;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.occupys = new boolean[width][height];
    }

    public World build() {
        return new World(tiles, occupys);
    }

    private WorldBuilder mazeTiles() {
        MazeGenerator mazeGenerator = new MazeGenerator(this.width - 2);
        mazeGenerator.generateMaze();
        int[][] maze = mazeGenerator.getMaze();
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                occupys[width][height] = false;
                if (width == 0 || height == 0 || width == this.width - 1 || height == this.height - 1) {
                    tiles[width][height] = Tile.BOUNDS;
                }
                else {
                    switch (maze[width - 1][height - 1]) {
                        case 0:
                            tiles[width][height] = Tile.WALL;
                            break;
                        case 1:
                            tiles[width][height] = Tile.FLOOR;
                            break;
                    }
                }
            }
        }
        return this;
    }

    public WorldBuilder makeMaze() {
        return mazeTiles();
    }
}
