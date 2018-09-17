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
 * A simple timer.
 */
public class Timer {
    private long startTime;
    private long goalTime;
    private int goal;

    /**
     * Create a new AnimationTimer.
     * Framerate defaults to 1 fps.
     */
    public Timer() {
        this.goal = 1000;
        this.reset();
    }

    public Timer(int goal) {
        this.set(goal);
    }

    public long getTimeLeft() {
        return goalTime - System.currentTimeMillis();
    }

    public boolean isDone() {
        return getTimeLeft() <= 0L;
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.goalTime = startTime + goal;
    }

    public void set(int goal) {
        this.goal = goal;
        this.reset();
    }

    void setGoal(int goal) {
        this.goal = goal;
    }
}