package lzw.screen;

import lzw.asciiPanel.AsciiPanel;

public interface GlyphDelegate {

    void printGlyph(AsciiPanel terminal, int offsetX, int offsetY);
}