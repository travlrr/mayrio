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
import actors.core.StaticAnimation;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

public class QuestionBlock extends AnimatedActor {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(16, 16), "/sprites/items/question.png");
    }

    private Animation anim;
    private StaticAnimation used;
    private boolean isUsed;
    private boolean oneUp;

    public QuestionBlock(boolean oneUp) {
        this.anim = new Animation(10, sheet.getSprites(0, 1, 2, 3));
        this.used = new StaticAnimation(sheet.getSprite(4));
        this.oneUp = oneUp;
        this.setAnimation(anim);
        this.setGravity(false);
        this.setKinematic(true);
    }

    @Override
    public void act() {
        super.act();

        Player player = this.getOneIntersectingObject(Player.class);
        if (player != null) {
            if (Math.abs(player.getEdge(Direction.UP).y() - this.getEdge(Direction.DOWN).y()) < 12) {
                this.setAnimation(used);
                player.setSpeedY(-2);

                if (isUsed) {
                    return;
                }

                this.isUsed = true;
                Direction direction = Math.random() == 1 ? Direction.LEFT : Direction.RIGHT;
                if (oneUp) {
                    this.getWorld().addObject(new MushroomGreen(direction), this.getCenterX(), this.getY() - this.getImage().getHeight());
                } else {
                    this.getWorld().addObject(new MushroomRed(direction), this.getCenterX(), this.getY() - this.getImage().getHeight());
                }
            }
        }
    }
}