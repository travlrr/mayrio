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

import core.sprites.RenderLayer;
import mayflower.Actor;

import java.util.ArrayList;

/**
 * Mayrio's base Actor class.
 * Provides functionality for collision and layering.
 */
public class MayrioActor extends Actor {
    private RenderLayer layer;
    private ArrayList<MayrioActor> touching;

    @Override
    public void act() {
        // We set touching to null to be 100% sure the old ArrayList gets caught by the GC
        touching = null;
        touching = new ArrayList<>(this.getIntersectingObjects(MayrioActor.class));

        for (MayrioActor other : touching) {
            if (this.layer.isAbove(other.layer)) {
                // TODO: Figure out how to control object render order
                return;
            }

            // TODO: Force other actor to move away when collision occurs
        }
    }

    void setLayer(RenderLayer layer) {
        this.layer = layer;
    }
}