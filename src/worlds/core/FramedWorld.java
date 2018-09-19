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

import actors.characters.Player;
import actors.core.Direction;
import mayflower.Mayflower;
import mayflower.World;

import java.util.ArrayList;

public class FramedWorld extends World {
    protected static ArrayList<Frame> frames;
    private int currentFrame;
    private Player ply;

    protected FramedWorld(Player player) {
        this.ply = player;
    }

    @Override
    public void act() {
        if (getEdge().equals(Direction.RIGHT) && currentFrame + 1 < frames.size()) {
            nextFrame();
        }

        if (getEdge().equals(Direction.LEFT) && currentFrame - 1 >= 0) {
            previousFrame();
        }
    }

    private Direction getEdge() {
        if (ply.getEdge(Direction.RIGHT).x() >= this.getWidth()) {
            return Direction.RIGHT;
        } else if (ply.getEdge(Direction.LEFT).x() <= 0) {
            return Direction.LEFT;
        }
        return null;
    }

    public void init() {
        for (Frame frame : frames) {
            frame.init();
        }
    }

    private void nextFrame() {
        currentFrame++;
        Mayflower.setWorld(frames.get(currentFrame));
    }

    private void previousFrame() {
        currentFrame--;
        if (currentFrame < 0) {
            throw new IndexOutOfBoundsException();
        }
        Mayflower.setWorld(frames.get(currentFrame));
    }

    protected void registerFrame(Frame frame) {
        frames.add(frame);
    }
}
