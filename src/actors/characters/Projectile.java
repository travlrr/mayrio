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

import actors.core.AnimatedActor;
import actors.core.Animation;
import actors.core.Direction;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

public class Projectile extends AnimatedActor {
    private Direction direction;
    private static SpriteSheet sheet;
    private static Animation anim;

    static {
        sheet = new SpriteSheet(new Dimension(32, 16), "/sprites/hazard/cannon/projectile.png");
        anim = new Animation(10, sheet.getSprites(0, 1));
    }


    Projectile(Direction direction) {
        this.direction = direction;
        this.setAnimation(anim);
        this.setCollides(false);
        this.setGravity(false);
    }


    public void act() {
        this.move(direction);
        Player ply = getOneIntersectingObject(Player.class);
        if (ply != null) {
            this.getWorld().removeObject(this);
            ply.hurt();
        }
    }
}
