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

package core;

import actors.characters.Player;
import actors.core.Coordinate;
import core.sprites.SpriteSheet;
import core.util.log.LogLevel;
import core.util.log.MayrioLogger;
import mayflower.Mayflower;
import worlds.Stage1;
import worlds.core.MayrioWorld;

public class Main extends Mayflower {
    private static final MayrioLogger logger;
    private static Grid grid;
    private static Player player;

    static {
        logger = new MayrioLogger(Main.class);
    }

    private Main() {
        super("Mayrio", 1024, 896);
    }

    public static void main(String[] args) {
        new Main();
    }

    /**
     * Get the Grid.
     */
    public static Grid getGrid() {
        return grid;
    }

    /**
     * Get the Player.
     */
    public static Player getPlayer() {
        return player;
    }

    @Override
    public void init() {
        int width = Mayflower.getWidth();
        int height = Mayflower.getHeight();
        int cellSize = 64;
        int scaleMul = Mayflower.getWidth() / 256;

        SpriteSheet.setScale(scaleMul);

        grid = Grid.getInstance(width, height, cellSize);
        player = Player.get();

        MayrioWorld world = new Stage1(player);
        world.init();

        MayrioLogger.setLevel(LogLevel.ALL);

        Mayflower.setWorld(world);
        Mayflower.showBounds(true);
        Mayflower.showFPS(false);

        Coordinate pos = grid.gridToScreen(4, 8);
        Coordinate gpos = grid.gridToScreen(8, 10);
        Coordinate cpos = grid.gridToScreen(8, 8);
        world.addObject(player, pos.x(), pos.y());
    }
}