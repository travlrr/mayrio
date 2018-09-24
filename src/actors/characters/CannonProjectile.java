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

/**
 * Bullet fired out of a Cannon.
 */
public class CannonProjectile extends AnimatedActor {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(32, 16), "/sprites/hazard/cannon/projectile.png");
    }

    private Direction direction;
    private Animation anim;

    CannonProjectile(Direction direction) {
        anim = new Animation(20, sheet.getSprites(0, 1)).mirrorHorizontal().mirrorVertical();
        this.direction = direction;
        this.setAnimation(anim);
        this.setCollides(false);
        this.setGravity(false);
    }


    public void act() {
        super.act();
        this.moveDirect(4, direction);
        Player ply = getOneIntersectingObject(Player.class);
        if (ply != null && !ply.isCrouching()) {
            ply.hurt();
        }
    }
}
