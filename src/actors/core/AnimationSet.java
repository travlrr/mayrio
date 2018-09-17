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

import core.util.log.MayrioLogger;

import java.util.HashMap;

/**
 * The AnimationSet class provides an easy way to group and name Animations.
 */
public class AnimationSet {
    private static final MayrioLogger logger;

    static {
        logger = new MayrioLogger(AnimationSet.class);
    }

    private HashMap<String, Animation> animations;

    /**
     * Constructs a new AnimationSet.
     * The two arrays passed to this constructor should be equal in length.
     *
     * @param animations Animations to put in the set
     * @param names      Names for each animation
     */
    public AnimationSet(Animation[] animations, String[] names) {
        this.animations = new HashMap<>();

        if (animations.length != names.length) {
            throw new IllegalArgumentException("Length of arrays passed to AnimationSet were not equal!");
        }

        for (int i = 0; i < animations.length; i++) {
            this.animations.put(names[i], animations[i]);
        }
    }

    /**
     * Add an animation to the set.
     *
     * @param animation Animation to put in the set
     * @param name      Name of the new animation
     */
    public void addAnimation(Animation animation, String name) {
        this.animations.put(name, animation);
    }

    /**
     * Get an animation from the set.
     *
     * @param name Name of animation to get
     * @return Animation, if it exists
     */
    Animation getAnimation(String name) {
        return this.animations.get(name);
    }
}