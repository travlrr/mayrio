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
import actors.core.PhysActor;
import actors.core.StaticActor;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;
import core.util.Timer;

public class Cannon extends StaticActor {
    private static SpriteSheet sheet;

    private Timer spawnTimer = new Timer();
    private int x = this.getX();
    private int y = this.getY();
    private Direction direction;

    static {
        sheet = new SpriteSheet(new Dimension(16, 32), "/sprites/hazard/cannon/cannon.png");
    }

    public Cannon(Direction direction) {
        super(sheet.getSprite(0), true);

        this.direction = direction;
        spawnTimer.set(3000);
    }

    @Override
    public void act() {
        super.act();
        if (spawnTimer.isDone()) {
            Projectile bullet = new Projectile(direction);
            int bx;
            bullet.setLocation(x - bullet.getImage().getWidth(), y);
            spawnTimer.reset();
        }
    }
}
