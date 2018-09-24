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

import mayflower.Actor;
import worlds.core.MayrioWorld;

public class MayrioActor extends Actor {
    private boolean collides;

    boolean collides() {
        return collides;
    }

    protected void setCollides(boolean collides) {
        this.collides = collides;
    }

    /**
     * Gets the center coordinate of an edge of this MayrioActor
     *
     * @param direction Side of edge to get
     * @return Coordinate
     */
    public Coordinate getEdge(Direction direction) {
        int x = this.getCenterX();
        int y = this.getCenterY();
        int w = this.getImage().getWidth();
        int h = this.getImage().getHeight();

        switch (direction) {
            case UP:
                return new Coordinate(x, y - (h / 2));
            case RIGHT:
                return new Coordinate(x + (w / 2), y);
            case DOWN:
                return new Coordinate(x, y + (h / 2));
            case LEFT:
                return new Coordinate(x - (w / 2), y);
        }
        assert false;
        return null;
    }

    @Override
    public void act() {
    }

    @Override
    public MayrioWorld getWorld() {
        return (MayrioWorld) super.getWorld();
    }
}