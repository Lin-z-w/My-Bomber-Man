package lzw;

import lzw.world.Creature;
import lzw.world.CreatureFactory;
import lzw.world.World;
import lzw.world.WorldBuilder;
import org.junit.Test;

public class BombAITest {
    World world = new WorldBuilder(30, 30).makeMaze().build();

    CreatureFactory creatureFactory = new CreatureFactory(world);

    Creature[] bomb = {creatureFactory.newBomb(1,1), creatureFactory.newBomb(2,2), creatureFactory.newBomb(3,3)};
    @Test
    public void testOnUpdate() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (bomb[j] != null) bomb[j].update();
            }
        }
    }
}
