package lzw.world;

import lzw.asciiPanel.AsciiPanel;
import java.awt.Color;
import java.io.Serializable;

public enum Tile implements Serializable {

    FLOOR((char) 219, AsciiPanel.black),

    BOMBFLOOR((char) 219, AsciiPanel.white),

    WALL((char) 176, AsciiPanel.brightBlack),

    BOUNDS((char) 177, AsciiPanel.brightBlack);

    private final char glyph;

    public char glyph() {
        return glyph;
    }

    private final Color color;

    public Color color() {
        return color;
    }

    public boolean isDiggable() {
        return this != Tile.WALL && this != Tile.BOUNDS;
    }

    public boolean isGround() {
        return this != Tile.WALL && this != Tile.BOUNDS;
    }

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }
}
