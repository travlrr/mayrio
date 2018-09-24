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
import actors.core.StaticActor;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;
import core.util.Timer;

/**
 * A formidable hazard. Fires a bullet at a regular interval.
 */
public class Cannon extends StaticActor {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(16, 32), "/sprites/hazard/cannon/cannon.png");
    }

    private Timer spawnTimer = new Timer();
    private int x;
    private int y;
    private Direction direction;

    public Cannon(Direction direction) {
        super(sheet.getSprite(0), false);
        this.direction = direction;
        spawnTimer.set(2500);
    }

    @Override
    public void act() {
        super.act();
        if (spawnTimer.isDone()) {
            x = this.getX();
            y = this.getY();

            CannonProjectile bullet = new CannonProjectile(direction);
            this.getWorld().addObject(bullet, x - bullet.getImage().getWidth(), y);
            spawnTimer.reset();
        }
    }
}
