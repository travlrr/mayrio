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

import actors.characters.Coin;
import actors.characters.Ground;
import actors.characters.Player;
import actors.core.Coordinate;
import actors.core.GroundType;
import core.sprites.SpriteSheet;
import core.util.log.LogLevel;
import core.util.log.MayrioLogger;
import mayflower.Mayflower;
import worlds.ActorTestWorld;

public class Main extends Mayflower {
    private static final MayrioLogger logger;
    private static Grid grid;

    static {
        logger = new MayrioLogger(Main.class);
    }

    private Main() {
        super("Mayrio", 512, 448);
    }

    public static void main(String[] args) {
        new Main();
    }

    public static Grid getGrid() {
        return grid;
    }

    @Override
    public void init() {
        int width = Mayflower.getWidth();
        int height = Mayflower.getHeight();
        int cellSize = 32;

        grid = Grid.getInstance(width, height, cellSize);

        SpriteSheet.setScale(2);
        Player player = Player.get();
        Ground ground = new Ground(GroundType.MIDDLE);
        Coin coin = new Coin();
        ActorTestWorld world = ActorTestWorld.get();
        world.init();
        MayrioLogger.setLevel(LogLevel.ALL);

        Mayflower.setWorld(world);
        Mayflower.showBounds(false);
        Mayflower.showFPS(false);

        Coordinate pos = grid.gridToScreen(4, 8);
        Coordinate gpos = grid.gridToScreen(8, 10);
        Coordinate cpos = grid.gridToScreen(8, 8);
        world.addObject(player, pos.x(), pos.y());
        world.addObject(ground, gpos.x(), gpos.y());
        world.addObject(coin, cpos.x(), cpos.y());
    }
}