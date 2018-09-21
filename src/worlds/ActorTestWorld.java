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

package worlds;

import actors.characters.Player;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;
import mayflower.Label;
import mayflower.MayflowerImage;
import worlds.core.MayrioWorld;
import worlds.core.WorldBuilder;

/**
 * Placeholder World for debugging
 */
public class ActorTestWorld extends MayrioWorld {
    private static ActorTestWorld instance;
    private Label points;

    private ActorTestWorld() {
    }

    public static ActorTestWorld get() {
        if (instance == null) {
            instance = new ActorTestWorld();
        }
        return instance;
    }

    @Override
    public void init() {
        WorldBuilder.setWorld(instance);
        WorldBuilder.createFlatGround();

        points = new Label("Points: 0", 24);
        instance.addObject(points, 128, 128);

        SpriteSheet background = new SpriteSheet(new Dimension(512, 432), "/sprites/background.png");
        MayflowerImage a = background.getSprite(0);
        this.setBackground(background.getSprite(0));
    }

    @Override
    public void act() {
        Player ply = this.getObjects(Player.class).get(0);
        points.setText(String.format("Points: %d", ply.getPoints()));
    }
}