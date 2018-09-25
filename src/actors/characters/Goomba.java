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

import actors.core.Animation;
import actors.core.Direction;
import actors.core.Walker;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

public class Goomba extends Walker {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(16, 16), "/sprites/hazard/goomba.png");
    }

    private Animation anim;

    public Goomba(Direction direction) {
        super(direction, 1);
        anim = new Animation(10, sheet.getSprites(0, 1)).mirrorVertical().mirrorHorizontal();
        this.setAnimation(anim);
        this.setGravity(false);
        this.setCollides(false);
    }

    @Override
    public void act() {
        super.act();
        Player player = this.getOneIntersectingObject(Player.class);
        if (player != null) {
            if (Math.abs(player.getEdge(Direction.DOWN).y() - this.getEdge(Direction.UP).y()) < 12) {
                this.die();
                player.setSpeedY(player.getMaxSpeedY() / 2);
            } else {
                player.hurt();
            }
        }
    }
}