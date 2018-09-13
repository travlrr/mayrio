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

import actors.characters.Ground;
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
    private int maxMoveSpeed;
    private int currentMoveSpeed;
    private int maxVerticalSpeed;
    private int currentVerticalSpeed;
    private boolean grounded;

    @Override
    public void act() {
        // We set touching to null to be 100% sure the old ArrayList gets caught by the GC
        touching = null;
        touching = new ArrayList<>(this.getIntersectingObjects(MayrioActor.class));

        // Layering
        for (MayrioActor other : touching) {
            if (this.layer.isAbove(other.layer)) {
                // TODO: Figure out how to control object render order
                return;
            }
        }

        /*
         * Physics
         */
        for (Actor other : touching) {
            // Set grounded
            if (other instanceof Ground) {
                Ground ground = (Ground) other;
                if (this.getEdge(Direction.DOWN).getY() == ground.getEdge(Direction.UP).getY()) {
                    grounded = true;
                }
            }

            // TODO: Move away when collision occurs
            if (other instanceof MayrioActor) {
                MayrioActor actor = (MayrioActor) other;
            } else if (other instanceof StaticActor) {
                StaticActor actor = (StaticActor) other;
                if (!actor.collides()) {
                    return;
                }

            }
        }

        // Decrease vertical speed if in air
        if (!grounded) {
            this.currentVerticalSpeed -= 1;
        } else {
            this.currentVerticalSpeed = 0;
        }
    }

    void setLayer(RenderLayer layer) {
        this.layer = layer;
    }

    public void move(int distance, Direction direction) {
        this.setRotation(direction.getAngle());
        super.move(distance);
    }

    public void jump() {
        this.currentVerticalSpeed = maxVerticalSpeed;
    }

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

        return null;
    }
}