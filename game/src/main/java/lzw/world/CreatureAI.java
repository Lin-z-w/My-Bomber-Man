/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package lzw.world;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Aeranythe Echosong
 */
abstract class CreatureAI implements Serializable {

    protected Creature creature;

    public CreatureAI(Creature creature) {
        this.creature = creature;
        this.creature.setAI(this);
    }

    public abstract void onEnter(int x, int y, Tile tile);

    public abstract void onUpdate();

    public abstract void attack(Creature another);

    public abstract void put(int direction);

    public abstract void onNotify(String message);

    public boolean canSee(int x, int y) {
//        if ((creature.x() - x) * (creature.x() - x) + (creature.y() - y) * (creature.y() - y) > creature.visionRadius()
//                * creature.visionRadius()) {
//            return false;
//        }
//        for (Point p : new Line(creature.x(), creature.y(), x, y)) {
//            if (creature.tile(p.x, p.y).isGround() || (p.x == x && p.y == y)) {
//                continue;
//            }
//            return false;
//        }
        return true;
    }
}
