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

import mayflower.MayflowerImage;

public class Animation {

    private MayflowerImage[] frames;
    private int currentFrame;
    private int frameRate;

    public Animation(int rate, String[] filenames) {
        frames = new MayflowerImage[filenames.length];

        for (int i = 0; i < filenames.length; i++) {
            frames[i] = new MayflowerImage(filenames[i]);
        }

        currentFrame = 0;
        frameRate = rate;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public MayflowerImage getNextFrame() {
        currentFrame = currentFrame % frames.length;
        MayflowerImage ret = frames[currentFrame];
        currentFrame = currentFrame + 1;
        return ret;
    }

    public void resize(int w, int h) {
        for (MayflowerImage p : frames) {
            p.scale(w, h);
        }
    }

    public void setTransparency(int percent) {
        for (MayflowerImage p : frames) {
            p.setTransparency(percent);
        }
    }

}