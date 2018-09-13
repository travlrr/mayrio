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

import actors.core.StaticActor;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;
import core.util.log.LogLevel;
import core.util.log.MayrioLogger;
import mayflower.Mayflower;
import mayflower.MayflowerImage;
import worlds.ActorTestWorld;

public class Main extends Mayflower {
    private static final MayrioLogger logger;

    static {
        logger = new MayrioLogger(Main.class);
    }

    private Main() {
        super("Mayrio", 512, 448);
    }

    @Override
    public void init() {
        // Test code for SpriteSheet

        ActorTestWorld world = new ActorTestWorld();
        MayrioLogger.setLevel(LogLevel.ALL);

        SpriteSheet.setScale(4);
        SpriteSheet sheet = new SpriteSheet(new Dimension(16, 32), "/sprites/mario_large.png");
        MayflowerImage testSprite = sheet.getSprite(1);
        StaticActor testActor = new StaticActor(testSprite, false);

        Mayflower.setWorld(world);
        world.addObject(testActor, 16, 16);
    }

    public static void main(String[] args) {
        new Main();
    }
}