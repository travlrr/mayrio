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

package actors.characters;

import actors.core.*;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;
import core.util.log.MayrioLogger;
import mayflower.Keyboard;
import mayflower.Mayflower;

public class Player extends AnimatedActor {
    private static final MayrioLogger logger;
    private static Player instance;
    private static SpriteSheet sm_mario;
    private static SpriteSheet lg_mario;

    static {
        sm_mario = new SpriteSheet(new Dimension(16, 24), "/sprites/mario_small.png");
        lg_mario = new SpriteSheet(new Dimension(16, 32), "/sprites/mario_large.png");
        logger = new MayrioLogger(Player.class);
    }


    private AnimationSet set_sm;
    private AnimationSet set_lg;
    private Direction facing;
    private boolean shroomed;
    private int points;

    private Player() {
        this.shroomed = false;
        this.facing = Direction.RIGHT;
        this.points = 0;
        this.setMaxSpeedX(3);
        this.setMaxSpeedY(10);

        // Small Mario animations
        Animation sm_idleRight = new StaticAnimation(sm_mario.getSprite(0)).mirrorHorizontal();
        Animation sm_idleLeft = sm_idleRight.mirrorVertical();
        Animation sm_crouchRight = new StaticAnimation(sm_mario.getSprite(2)).mirrorHorizontal();
        Animation sm_crouchLeft = sm_crouchRight.mirrorVertical();
        Animation sm_walkRight = new Animation(15, sm_mario.getSprites(0, 1)).mirrorHorizontal();
        Animation sm_walkLeft = sm_walkRight.mirrorVertical();
        Animation sm_jumpRight = new StaticAnimation(sm_mario.getSprite(3)).mirrorHorizontal();
        Animation sm_jumpLeft = sm_jumpRight.mirrorVertical();
        Animation sm_fallRight = new StaticAnimation(sm_mario.getSprite(4)).mirrorHorizontal();
        Animation sm_fallLeft = sm_fallRight.mirrorVertical();

        // Initialize
        Animation[] anims_sm = {sm_idleLeft, sm_idleRight, sm_crouchLeft, sm_crouchRight, sm_walkLeft, sm_walkRight, sm_jumpRight, sm_jumpLeft, sm_fallRight, sm_fallLeft};
        String[] names_sm = {"idleLeft", "idleRight", "crouchLeft", "crouchRight", "walkLeft", "walkRight", "jumpRight", "jumpLeft", "fallRight", "fallLeft"};
        set_sm = new AnimationSet(anims_sm, names_sm);
        this.setAnimationSet(set_sm);
        this.setAnimation("idleRight");
    }

    public static Player get() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    @Override
    public void act() {
        super.act();

        // Movement and animation
        if (Mayflower.isKeyDown(Keyboard.KEY_UP)) {
            jump();
        }
        if (!isGrounded()) {
            if (getSpeedY() > 0) {
                this.setAnimation(facing == Direction.LEFT ? "jumpLeft" : "jumpRight");
            } else if (getSpeedY() < -1) {
                this.setAnimation(facing == Direction.LEFT ? "fallLeft" : "fallRight");
            }
        } else if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT)) {
            if (isGrounded()) {
                this.setAnimation("walkRight");
            }
            if (facing == Direction.LEFT) {
                turn();
            }
            this.facing = Direction.RIGHT;
            accel();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_LEFT)) {
            if (isGrounded()) {
                this.setAnimation("walkLeft");
            }
            if (facing == Direction.RIGHT) {
                turn();
            }
            this.facing = Direction.LEFT;
            accel();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_DOWN)) {
            this.slow();
            if (facing.equals(Direction.LEFT)) {
                this.setAnimation("crouchLeft");
            } else if (facing.equals(Direction.RIGHT)) {
                this.setAnimation("crouchRight");
            }
        } else {
            this.slow();
            if (this.getSpeedX() == 0) {
                if (facing.equals(Direction.LEFT)) {
                    this.setAnimation("idleLeft");
                } else if (facing.equals(Direction.RIGHT)) {
                    this.setAnimation("idleRight");
                }
            }
        }

        this.move(facing);
    }

    private void turn() {
        Dust dust = new Dust();

        int x = this.getX() + this.getImage().getWidth() / 2;
        int y = this.getY() + this.getImage().getHeight();

        this.getWorld().addObject(dust, x - 4, y - 4);
        this.setSpeedX(getSpeedX() / 4);
    }

    void addPoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }
}