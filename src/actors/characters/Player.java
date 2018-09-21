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
import core.util.ImageUtils;
import core.util.Timer;
import core.util.log.LogLevel;
import core.util.log.MayrioLogger;
import mayflower.Keyboard;
import mayflower.Mayflower;
import mayflower.MayflowerImage;

public class Player extends AnimatedActor {
    private static final MayrioLogger logger;
    private static Player instance;
    private static SpriteSheet mario_sm;
    private static SpriteSheet mario_lg;

    static {
        mario_sm = new SpriteSheet(new Dimension(16, 24), "/sprites/mario_small.png");
        mario_lg = new SpriteSheet(new Dimension(16, 32), "/sprites/mario_large.png");
        logger = new MayrioLogger(Player.class);
    }


    private AnimationSet set_sm;
    private AnimationSet set_lg;
    private Animation death;
    private double deathMovement;
    private Direction facing;
    private Timer hurtTimer;
    private boolean shroomed;
    private boolean dead;
    private int points;
    private int lives;

    private Player() {
        // Instance variables
        this.deathMovement = 4.0;
        this.hurtTimer = new Timer();
        this.facing = Direction.RIGHT;
        this.shroomed = false;
        this.points = 0;
        this.lives = 5;

        // Movement variables
        this.setMaxSpeedX(6);
        this.setMaxSpeedY(10);

        // Small Mario animations
        Animation sm_idleRight = new StaticAnimation(mario_sm.getSprite(0)).mirrorHorizontal();
        Animation sm_idleLeft = sm_idleRight.mirrorVertical();
        Animation sm_crouchRight = new StaticAnimation(mario_sm.getSprite(2)).mirrorHorizontal();
        Animation sm_crouchLeft = sm_crouchRight.mirrorVertical();
        Animation sm_walkRight = new Animation(15, mario_sm.getSprites(0, 1)).mirrorHorizontal();
        Animation sm_walkLeft = sm_walkRight.mirrorVertical();
        Animation sm_jumpRight = new StaticAnimation(mario_sm.getSprite(3)).mirrorHorizontal();
        Animation sm_jumpLeft = sm_jumpRight.mirrorVertical();
        Animation sm_fallRight = new StaticAnimation(mario_sm.getSprite(4)).mirrorHorizontal();
        Animation sm_fallLeft = sm_fallRight.mirrorVertical();

        // Large Mario animations
        Animation lg_idleRight = new StaticAnimation(mario_lg.getSprite(0)).mirrorHorizontal();
        Animation lg_idleLeft = lg_idleRight.mirrorVertical();
        Animation lg_crouchRight = new StaticAnimation(mario_lg.getSprite(3)).mirrorHorizontal();
        Animation lg_crouchLeft = lg_crouchRight.mirrorVertical();
        Animation lg_walkRight = new Animation(15, mario_lg.getSprites(0, 1, 2)).mirrorHorizontal();
        Animation lg_walkLeft = lg_walkRight.mirrorVertical();
        Animation lg_jumpRight = new StaticAnimation(mario_lg.getSprite(4)).mirrorHorizontal();
        Animation lg_jumpLeft = lg_jumpRight.mirrorVertical();
        Animation lg_fallRight = new StaticAnimation(mario_lg.getSprite(5)).mirrorHorizontal();
        Animation lg_fallLeft = lg_fallRight.mirrorVertical();

        // Death animation
        MayflowerImage[] deathFrames = new MayflowerImage[]{
                mario_sm.getSprite(8),
                ImageUtils.mirrorImageHorizontal(mario_sm.getSprite(8))
        };
        death = new Animation(15, deathFrames);

        // Initialize
        String[] names = {"idleLeft", "idleRight", "crouchLeft", "crouchRight", "walkLeft", "walkRight", "jumpRight", "jumpLeft", "fallRight", "fallLeft"};

        Animation[] anims_sm = {sm_idleLeft, sm_idleRight, sm_crouchLeft, sm_crouchRight, sm_walkLeft, sm_walkRight, sm_jumpRight, sm_jumpLeft, sm_fallRight, sm_fallLeft};
        set_sm = new AnimationSet(anims_sm, names);

        Animation[] anims_lg = {lg_idleLeft, lg_idleRight, lg_crouchLeft, lg_crouchRight, lg_walkLeft, lg_walkRight, lg_jumpRight, lg_jumpLeft, lg_fallRight, lg_fallLeft};
        set_lg = new AnimationSet(anims_lg, names);

        this.setAnimationSet(set_sm);
        this.setAnimation("idleRight");
    }

    /**
     * Get the Player instance
     */
    public static Player get() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    @Override
    public void act() {
        super.act();


        if (shroomed && !this.getAnimations().equals(set_lg)) {
            this.setAnimationSet(set_lg);
        } else if (!shroomed && !this.getAnimations().equals(set_sm)) {
            this.setAnimationSet(set_sm);
        }

        // Die if below world
        if (this.getY() > Mayflower.getHeight()) {
            this.kill();
        }

        // Death animation
        if (dead) {
            this.deathMovement -= 0.2;
            this.setLocation((double) this.getX(), (double) this.getY() - this.deathMovement);

            return;
        }

        // Movement and animation
        if (Mayflower.isKeyDown(Keyboard.KEY_UP) || Mayflower.isKeyDown(Keyboard.KEY_W)) {
            jump();
        }

        // Jump animations
        if (!isGrounded()) {
            if (getSpeedY() > 0) {
                this.setAnimation(facing == Direction.LEFT ? "jumpLeft" : "jumpRight");
            } else if (getSpeedY() < -1) {
                this.setAnimation(facing == Direction.LEFT ? "fallLeft" : "fallRight");
            }
        }

        if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) || Mayflower.isKeyDown(Keyboard.KEY_D)) {
            if (isGrounded()) {
                this.setAnimation("walkRight");
            }
            if (facing == Direction.LEFT) {
                turn();
            }
            this.facing = Direction.RIGHT;
            accel();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) || Mayflower.isKeyDown(Keyboard.KEY_A)) {
            if (isGrounded()) {
                this.setAnimation("walkLeft");
            }
            if (facing == Direction.RIGHT) {
                turn();
            }
            this.facing = Direction.LEFT;
            accel();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_DOWN) || Mayflower.isKeyDown(Keyboard.KEY_S)) {
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


    /**
     * Damage the player.
     */
    public void hurt() {
        if (hurtTimer.getTimeLeft() > 0) {
            return;
        }

        hurtTimer.set(1000);

        if (shroomed) {
            shroomed = false;
            this.setAnimationSet(set_sm);
        } else {
            kill();
        }
    }

    /**
     * Called when switching directions. Spawns a Dust object and sets speed to 1/4.
     */
    private void turn() {
        if (isGrounded()) {
            Dust dust = new Dust();
            int x = this.getX() + this.getImage().getWidth() / 2;
            int y = this.getY() + this.getImage().getHeight();

            this.getWorld().addObject(dust, x - 4, y - 4);
        }

        this.setSpeedX(getSpeedX() / 4);
    }

    /**
     * Kill the player instantly.
     */
    private void kill() {
        if (dead) {
            return;
        }
        dead = true;
        this.setAnimation(death);
        this.setGravity(false);
        this.setCollides(false);
        logger.logf(LogLevel.INFO, "Player died! %d lives left.", lives);
    }

    /**
     * Add a point.
     */
    void addPoint() {
        points++;
    }

    /**
     * Return the player's point count.
     */
    public int getPoints() {
        return points;
    }
}