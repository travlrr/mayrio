/*
 * This file is part of mayrio.
 *
 * mayrio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mayrio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mayrio.  If not, see <https://www.gnu.org/licenses/>.
 */

package worlds.frames.stage1;

import actors.characters.Cannon;
import actors.characters.Goomba;
import actors.core.Direction;
import core.Grid;
import worlds.core.Frame;
import worlds.core.FramedWorld;
import worlds.core.WorldBuilder;

/**
 * Frame 3 of stage 1
 */
public class Stage1f3 extends Frame {
    public Stage1f3(FramedWorld parent) {
        super(parent);
    }

    @Override
    protected void init() {
        WorldBuilder.setWorld(this.getParent());
        WorldBuilder.createFlatGround();
        Goomba g1 = new Goomba(Direction.LEFT);
        Goomba g2 = new Goomba(Direction.LEFT);
        Goomba g3 = new Goomba(Direction.LEFT);
        Cannon c = new Cannon(Direction.RIGHT);
        this.addObject(g1, Grid.toScreen(8, 10));
        this.addObject(g2, Grid.toScreen(7, 10));
        this.addObject(g3, Grid.toScreen(6, 10));
        this.addObject(c, Grid.toScreen(2, 9));
    }
}