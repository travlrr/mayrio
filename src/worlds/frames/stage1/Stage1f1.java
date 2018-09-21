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
import actors.core.Coordinate;
import actors.core.Direction;
import core.Grid;
import core.Main;
import worlds.core.Frame;
import worlds.core.FramedWorld;
import worlds.core.WorldBuilder;

public class Stage1f1 extends Frame {
    public Stage1f1(FramedWorld parent) {
        super(parent);
    }

    @Override
    protected void init() {
        WorldBuilder.setWorld(this.getParent());
        WorldBuilder.createFlatGround();

        Grid grid = Main.getGrid();
        Coordinate pos = grid.gridToScreen(5, 7);
        Cannon c = new Cannon(Direction.LEFT);
        addObject(c, pos.x(), pos.y());
    }
}
