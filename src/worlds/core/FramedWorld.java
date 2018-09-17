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
import mayflower.World;

import java.util.ArrayList;

public class FramedWorld extends World {
    private ArrayList<World> frames;
    private int currentFrame;
    private Player ply;

    protected FramedWorld(Player player) {
        this.ply = player;
    }

    @Override
    public void act() {
    }

    private Direction getEdge() {
        if (ply.getEdge(Direction.RIGHT).x() >= this.getWidth()) {
            return Direction.RIGHT;
        } else if (ply.getEdge(Direction.LEFT).x() <= 0) {
            return Direction.LEFT;
        }
        return null;
    }

    public void addFrame(int pos, Frame frame) {
        frames.add(pos, frame);
        frame.setParent(this);
    }

    public void nextFrame() {

    }

    public void previousFrame() {

    }
}
