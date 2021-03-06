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

import actors.core.Coordinate;
import mayflower.Actor;

public abstract class Frame {
    private FramedWorld parent;

    public Frame(FramedWorld parent) {
        this.parent = parent;
    }

    protected abstract void init();

    /**
     * Get this Frame's parent FrameWorld.
     */
    protected FramedWorld getParent() {
        return parent;
    }

    protected void addObject(Actor object, int x, int y) {
        this.parent.addObject(object, x, y);
    }

    protected void addObject(Actor object, Coordinate pos) {
        this.parent.addObject(object, pos);
    }
}