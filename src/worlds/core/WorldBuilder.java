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

package worlds.core;

import actors.characters.Ground;
import actors.core.Coordinate;
import actors.core.GroundType;
import core.Grid;
import core.Main;
import mayflower.Mayflower;
import mayflower.World;

public class WorldBuilder {
    private static Grid grid;
    private static World world;

    static {
        WorldBuilder.grid = Main.getGrid();
    }

    public static void setWorld(World world) {
        WorldBuilder.world = world;
    }


    public static void createFlatGround() {
        for (int x = 0; x < grid.getCellsX(); x++) {
            Ground ground1 = new Ground(GroundType.FLAT_TOP);
            Ground ground2 = new Ground(GroundType.MIDDLE);
            Coordinate coords = grid.gridToScreen(x, 0);
            world.addObject(ground1, coords.x(), Mayflower.getHeight() - ground1.getImage().getHeight() * 3);
            world.addObject(ground2, coords.x(), Mayflower.getHeight() - ground1.getImage().getHeight() * 2);
        }
    }

    public static void createPitfall() {
        for (int x = 0; x < grid.getCellsX(); x++) {
            Ground ground1 = new Ground(GroundType.FLAT_TOP);
            Ground ground2 = new Ground(GroundType.MIDDLE);
            Coordinate coords = grid.gridToScreen(x, 0);
            world.addObject(ground1, coords.x(), Mayflower.getHeight() - ground1.getImage().getHeight() * 3);
            world.addObject(ground2, coords.x(), Mayflower.getHeight() - ground1.getImage().getHeight() * 2);
        }
    }
}