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

package actors.characters;

import actors.core.Direction;
import actors.core.MayrioActor;
import actors.core.StaticAnimation;
import actors.core.Walker;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

public class MushroomGreen extends Walker {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(16, 16), "/sprites/items/powerups.png");
    }

    MushroomGreen(Direction direction) {
        super(direction, 1);
        this.setAnimation(new StaticAnimation(sheet.getSprite(2)));
        this.setGravity(false);
        this.setCollides(false);
    }

    @Override
    public void act() {
        super.act();
        if (!isTouching(MayrioActor.class)) {
            moveDirect(0, 2);
        }

        Player player = this.getOneIntersectingObject(Player.class);
        if (player != null) {
            this.die();
            player.addLife();
        }
    }
}
