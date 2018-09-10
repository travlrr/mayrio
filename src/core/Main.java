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

import actors.StaticActor;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;
import core.util.log.MayrioLogger;
import mayflower.MayflowerImage;

public class Main {
    private static final MayrioLogger logger = new MayrioLogger(Main.class);

    public static void main(String[] args) {
        /*
         * TODO: Figure out how to initialize a Mayflower window
         * Mayflower 3 introduces a lot of changes from Mayflower 1 and 2 and the
         * documentation isn't available online, so we'll figure this out later
         */

        // Test code for SpriteSheet
        SpriteSheet sheet = new SpriteSheet(new Dimension(16, 32), "/sprites/mario_large.png");
        MayflowerImage testSprite = sheet.getSprite(1);
        StaticActor testActor = new StaticActor(testSprite, false);
    }
}