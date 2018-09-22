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
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

public class Coin extends AnimatedActor {
    private static SpriteSheet sheet;
    private static Animation anim;
    private static Animation anim_grey;
    private static boolean grey;

    static {
        sheet = new SpriteSheet(new Dimension(16, 16), "/sprites/items/coins.png");
        anim = new Animation(10, sheet.getSprites(0, 1, 2, 3));
        anim_grey = new Animation(10, sheet.getSprites(4, 5, 6, 7));
    }

    public Coin() {
        anim.reset();
        grey = false;
        this.setAnimation(anim);
        this.setCollides(false);
        this.setGravity(false);
    }

    @Override
    public void act() {
        super.act();
        Player ply = getOneIntersectingObject(Player.class);
        if (ply != null) {
            this.getWorld().removeObject(this);
            ply.addPoint();
        }
    }
}
