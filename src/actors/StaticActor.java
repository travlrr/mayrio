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

package actors;

import mayflower.Actor;
import mayflower.MayflowerImage;

/**
 * StaticActors are a simple extension of the Actor class that do not move.
 * These are ideal for ground tiles, backgrounds, and the like.
 */
public class StaticActor extends Actor {
    private MayflowerImage sprite;
    private boolean collides;

    public StaticActor(MayflowerImage sprite, boolean enableCollision) {
        this.sprite = sprite;
        this.collides = enableCollision;
    }

    @Override
    public void act() {
    }
}
