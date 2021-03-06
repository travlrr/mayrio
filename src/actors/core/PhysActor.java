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

import java.util.ArrayList;
import java.util.List;

/**
 * Mayrio's base Actor class.
 * Provides functionality for collision and layering.
 */
public class PhysActor extends MayrioActor {
    private double maxSpeedX;
    private double speedX;
    private double maxSpeedY;
    private double speedY;
    private boolean grounded;
    private boolean gravity;
    private boolean kinematic;

    PhysActor() {
        this.maxSpeedX = 1;
        this.maxSpeedY = 1;
        this.gravity = true;
        this.setCollides(true);
    }

    @Override
    public void act() {
        super.act();

        // Get all objects in the current world
        List<MayrioActor> objects = this.getWorld().getObjects(MayrioActor.class);
        List<Ground> nearbyGround = new ArrayList<>();

        // Physics
        for (MayrioActor other : objects) {
            // Handle collisions
            if (this.intersects(other) && other.collides() && this.collides()) {
                collide(other);
                if (other instanceof Ground) {
                    nearbyGround.add((Ground) other);
                }
            }
        }

        // Decrease vertical speed if in air
        if (nearbyGround.size() == 0) {
            grounded = false;
            this.speedY -= 0.5;
        } else {
            grounded = true;
            if (speedY < 0) {
                speedY = 0;
            }
        }

        // Move vertically
        if (gravity) {
            this.moveV();
        }

        // Never let speedX be below 0
        if (this.speedX < 0) {
            speedX = 0;
        }
    }

    private void collide(MayrioActor other) {
        if (kinematic) {
            return;
        }

        double left1 = this.getEdge(Direction.LEFT).dx();
        double right1 = this.getEdge(Direction.RIGHT).dx();
        double top1 = this.getEdge(Direction.UP).dy();
        double bottom1 = this.getEdge(Direction.DOWN).dy();

        double left2 = other.getEdge(Direction.LEFT).dx();
        double right2 = other.getEdge(Direction.RIGHT).dx();
        double top2 = other.getEdge(Direction.UP).dy();
        double bottom2 = other.getEdge(Direction.DOWN).dy();

        if (right1 >= left2) {
            moveDirect(-1, 0);
        }
        if (left1 <= right2) {
            moveDirect(1, 0);
        }
        if (bottom1 >= top2) {
            moveDirect(0, 1);
        }
        if (top1 <= bottom2) {
            moveDirect(0, -1);
        }
    }

    /**
     * Move in a given direction according to current speed
     *
     * @param direction Direction
     */
    protected void move(Direction direction) {
        this.setRotation(direction.getAngle());
        super.move(speedX);
    }

    /**
     * Move vertically according to current speed
     */
    private void moveV() {
        this.setRotation(Direction.UP.getAngle());
        super.move(speedY);
    }

    /**
     * Moves an object in the given direction
     */
    protected void moveDirect(int distance, Direction direction) {
        this.setRotation(direction.getAngle());
        super.move(distance);
    }

    /**
     * Move (x,y) amount of pixels
     */
    protected void moveDirect(double x, double y) {
        this.setLocation(this.getX() + x, this.getY() + y);
    }

    /**
     * Speed up
     */
    protected void accel() {
        if (this.speedX < maxSpeedX) {
            this.speedX += 0.1;
        }
    }

    /**
     * Slow down
     */
    protected void slow() {
        if (this.speedX > 0) {
            this.speedX -= 0.2;
        }
    }

    /**
     * Jump
     */
    protected void jump() {
        if (grounded) {
            this.speedY = maxSpeedY;
        }
        grounded = false;
    }

    protected double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getMaxSpeedY() {
        return maxSpeedY;
    }

    protected void setMaxSpeedY(int speed) {
        maxSpeedY = speed;
    }

    protected double getSpeedX() {
        return speedX;
    }

    protected void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    protected void setMaxSpeedX(int speed) {
        maxSpeedX = speed;
    }

    protected boolean isGrounded() {
        return grounded;
    }

    protected void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    protected void setKinematic(boolean kinematic) {
        this.kinematic = kinematic;
    }
}