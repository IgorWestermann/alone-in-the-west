/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs;

import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.State;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.AnimationHandler;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.mobs.controllers.MovimentController;
import com.mygdx.game.sprites.mobs.controllers.AttackType;

/**
 *
 * @author Hugo
 */
public abstract class Mob extends Entity {

    protected State currentState;
    protected Direction currentDirection;
    protected Direction lastDirection;
    protected MovimentController mController;
    protected AttackType attackType;
    //
    private boolean actionLock = false;
    //
    protected AnimationHandler animations;

    private State lockedState = null;
    private Direction lockedDirecion = null;

    protected boolean triggerDeath = false;

    // protected Body feet;
    public Mob(MapHandler mapHandler, EntityHandler entityHandler, short category, short[] collidesWith) {
        super(mapHandler, entityHandler, category, collidesWith);
        this.currentState = State.IDLE;
        this.currentDirection = Direction.S;
        this.lastDirection = Direction.S;

        defineThisBody(0, 0);
        entityHandler.watchEntity(this);
    }

    public Mob(MapHandler mapHandler, EntityHandler entityHandler, short category, short[] collidesWith, float startX, float startY) {
        super(mapHandler, entityHandler, category, collidesWith);
        this.currentState = State.IDLE;
        this.currentDirection = Direction.S;
        this.lastDirection = Direction.S;

        defineThisBody(startX, startY);
        entityHandler.watchEntity(this);
    }

    //nessa funcao a gente define cada caracteristica fisica do corpo
    protected abstract void defineThisBody(float startX, float startY);

    public void setActionLock(State state, Direction direction) {

        if (!actionLock && lockedDirecion == null && lockedState == null) {
            this.lockedState = state;
            this.lockedDirecion = direction;
            this.animations.setAnimationLock(state, direction);
            this.actionLock = true;
            this.setState(state);
        }

    }

    public void waitActionUnlock(float dt) {
        if (this.actionLock && this.animations.isCurrentAnimationFinished()) {
            //System.out.println("Action Unlocked");
            this.lockedState = null;
            this.lockedDirecion = null;
            this.actionLock = false;
        }
    }

    public AnimationHandler getAnimations() {
        return animations;
    }

    public abstract Direction getDirection();

    public void setState(State state) {
        currentState = state;
    }

    public State getState() {

        if (!actionLock) {
            if (this.body.getLinearVelocity().x != 0 || this.body.getLinearVelocity().y != 0) {
                return State.RUNNING;
            } else {
                return State.IDLE;
            }
        } else {
            return lockedState;
        }
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private int health = 2;

    private void movimentClamp() {
        float x = this.body.getLinearVelocity().x;
        float y = this.body.getLinearVelocity().y;

        if (Math.abs(x) < 5) {
            x = 0;
        }
        if (Math.abs(y) < 5) {
            y = 0;
        }
        this.body.setLinearVelocity(x, y);
    }

    @Override
    public void update(float dt) {
        if (!actionLock) {
            mController.move(this, dt);
            attackType.act(dt);
        } else {

            waitActionUnlock(dt);
        }

        verifyDeath(dt);
        movimentClamp();

        super.setPosition((body.getPosition().x - super.getWidth() / 2) + boxXOffset, (body.getPosition().y - super.getHeight() / 2) + boxYOffset);
        super.setRegion(getFrame(dt));

    }

    public void hitted() {

        setActionLock(State.HIT, this.getDirection());

        this.health--;

        System.out.println(this + " " + this.health);

        if (this.health < 0) {
            System.out.println(this + " to die");
            setActionLock(State.IDLE, Direction.S);
            triggerDeath = true;
        }
    }

    private void verifyDeath(float dt) {
        if (triggerDeath && this.animations.isCurrentAnimationFinished()) {
            super.setSelfDestruct();
        }
    }

}
