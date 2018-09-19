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

package actors.core;

import core.util.Timer;

public class Cannon extends PhysActor {

    private Timer t = new Timer(); //Timer variable, it is the frequency at which the bullets spawn
    private int x = this.getX(); //Used to determine where the bullet should spawn
    private int y = this.getY(); //Used to determine where the bullet should spawn
    private Direction direction;

    public Cannon(Direction direction) {
        this.direction = direction;
        t.set(3000);
    }

    @Override
    public void act() {

        super.act();

        if (t.isDone()) {
            Projectile bullet = new Projectile(direction);
            bullet.setLocation(x - bullet.getImage().getWidth(), y);
            t.reset();
        }
    }

}
