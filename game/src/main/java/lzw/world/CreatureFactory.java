package lzw.world;

import java.io.Serializable;
import java.util.List;

import lzw.asciiPanel.AsciiPanel;

public class CreatureFactory  implements Serializable {

    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages) {
        Creature player = new Creature(this.world, (char)2, AsciiPanel.brightWhite, 100, 20, 5, 9, true);
        world.addAtEmptyLocation(player);
        new PlayerAI(player, messages, this);
        return player;
    }

    public Creature newMonster() {
        Creature monster = new Creature(this.world, (char) 1, AsciiPanel.brightBlack, 10, 0, 0, 0, false);
        world.addAtEmptyLocation(monster);
        new MonsterAI(monster);
        return monster;
    }

    public Creature newBomb(int x, int y) {
        Creature bomb = new Creature(this.world, (char) 9, AsciiPanel.brightWhite, 1000, 0, 0, 0, false);
        if (!world.addAt(bomb, x, y)) return null;
        new BombAI(bomb);
        return bomb;
    }
}
