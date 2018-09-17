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

package core.util;

/**
 * Much like Timer, but uses framerates instead of milliseconds. Used internally to handle animations.
 */
public class AnimationTimer extends Timer {
    /**
     * Create a new AnimationTimer.
     * Framerate defaults to 1 fps.
     */
    public AnimationTimer() {
        super.setGoal(1000);
        super.reset();
    }

    public AnimationTimer(int framerate) {
        this.set(framerate);
    }

    @Override
    public void set(int framerate) {
        super.setGoal(1000 / framerate);
        this.reset();
    }
}