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

import java.awt.geom.Rectangle2D;
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

    public PhysActor() {
        this.grounded = false;
        this.maxSpeedX = 1;
        this.maxSpeedY = 1;
        this.gravity = true;
        this.setCollides(true);
    }

    @Override
    public void act() {
        // We set touching to null to be 100% sure the old ArrayList gets caught by the GC
        List<MayrioActor> objects = this.getWorld().getObjects(MayrioActor.class);

        /*
         * Physics
         */
        if (objects.size() == 0) {
            grounded = false;
        }

        for (MayrioActor other : objects) {
            // Handle collisions
            if (this.intersects(other)) {
                collide(other);
            }
        }

        // Decrease vertical speed if in air
        if (!grounded) {
            this.speedY -= 0.5;
        } else {
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
        if (!other.collides() || !this.collides()) {
            return;
        }

        if (other instanceof Ground) {
            grounded = true;
        }

        this.turnTowards(other);
        this.turn(180);
        Rectangle2D mB = this.getBounds().getBounds2D();
        Rectangle2D oB = other.getBounds().getBounds2D();

        double left1 = mB.getX();
        double left2 = oB.getX();
        double right1 = left1 + mB.getWidth();
        double right2 = left2 + oB.getWidth();
        double top1 = mB.getY();
        double top2 = oB.getY();
        double bottom1 = top1 + mB.getHeight();
        double bottom2 = top2 + oB.getHeight();

        double overlap_x = Math.max(0, Math.min(right1, right2) - Math.max(left1, left2));
        double overlap_y = Math.max(0, Math.min(bottom1, bottom2) - Math.max(top1, top2));

        if (other instanceof Ground) {
            overlap_x = 0;
        }

        if (Math.abs(overlap_y - oB.getHeight()) < 2) {
            overlap_y = 0;
        }

        this.setLocation(getX() - overlap_x, getY() - overlap_y);
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
}