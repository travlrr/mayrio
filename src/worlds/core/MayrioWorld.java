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

package worlds.core;

import actors.characters.*;
import actors.core.Coordinate;
import actors.core.TextActor;
import core.Main;
import mayflower.Actor;
import mayflower.World;

import java.util.Iterator;

public class MayrioWorld extends World {
    private TextActor points;
    private TextActor lives;

    public MayrioWorld() {
        this.setPaintOrder(Ground.class, Player.class, Dust.class, Cannon.class, CannonProjectile.class);
    }

    public void init() {
        Iterator<Actor> iterator = this.getObjects().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        this.addUi();
    }

    @Override
    public void act() {
        if (!(points == null) && !(lives == null)) {
            points.setText(String.format("Points: %d", Main.getPlayer().getPoints()));
            lives.setText(String.format("Lives: %d", Main.getPlayer().getLives()));
        }
    }

    protected void addUi() {
        points = new TextActor("Points: 0", 32);
        lives = new TextActor("Lives: 5", 32);
        this.addObject(points, 16, 0);
        this.addObject(lives, getWidth() - 16 - lives.getImage().getWidth(), 0);
    }

    public void addObject(Actor object, Coordinate pos) {
        super.addObject(object, pos.x(), pos.y());
    }
}