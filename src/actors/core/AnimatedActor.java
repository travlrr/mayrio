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

import core.util.AnimationTimer;

/**
 * AnimatedActor is a further extension of PhysActor that adds animation capabilities.
 */
public class AnimatedActor extends PhysActor {
    private AnimationSet animations;
    private Animation currentAnimation;
    private AnimationTimer animTimer;

    protected AnimatedActor() {
        animTimer = new AnimationTimer();
    }

    @Override
    public void act() {
        super.act();
        if (animTimer.getTimeLeft() <= 0 && currentAnimation != null) {
            this.setImage(currentAnimation.getNextFrame());
            animTimer.reset();
        }
    }

    /**
     * Get the current AnimationSet for this AnimatedActor
     */
    protected AnimationSet getAnimations() {
        return this.animations;
    }

    /**
     * Set current animation by name, assuming it exists in the AnimationSet
     */
    protected void setAnimation(String name) {
        if (this.getCurrentAnimation() == null || !this.getCurrentAnimation().equals(getAnimation(name))) {
            this.currentAnimation = animations.getAnimation(name);
            this.setImage(currentAnimation.getFirstFrame());
            animTimer.set(currentAnimation.getFrameRate());
        }
    }

    /**
     * Set current animation
     */
    protected void setAnimation(Animation anim) {
        if (this.getCurrentAnimation() == null || !this.getCurrentAnimation().equals(anim)) {
            this.currentAnimation = anim;
            this.setImage(currentAnimation.getFirstFrame());
            animTimer.set(currentAnimation.getFrameRate());
        }
    }

    /**
     * Set the current AnimationSet
     */
    protected void setAnimationSet(AnimationSet animations) {
        this.animations = animations;
    }

    /**
     * Get an animation from the current AnimationSet
     */
    private Animation getAnimation(String name) {
        return animations.getAnimation(name);
    }

    /**
     * Get the current Animation
     */
    protected Animation getCurrentAnimation() {
        return this.currentAnimation;
    }
}