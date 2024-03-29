package com.mygdx.game.entities.mobs;

import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.EntityHandler;
import com.mygdx.game.controllers.MapHandler;
import com.mygdx.game.sprites.AnimationHandler;
import com.mygdx.game.controllers.Entity;
import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.controllers.interfaces.AttackType;

import java.util.ArrayList;
import java.util.List;

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
    protected boolean dead = false;

    private String mobType = "";

    public String getMobType() {
        return mobType;
    }

    public void setMobType(String mobType) {
        this.mobType = mobType;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private List<Integer> life = new ArrayList<>();

    // protected Body feet;
    private int attackDamage = 0;
    private int speedModifier = 0;
    private int attackSpeed = 1;

    private float getAttackDuration() {

        return 1 / attackSpeed;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    private boolean isHit = false;

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(int speedModifier) {
        this.speedModifier = speedModifier;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

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
    // nessa funcao a gente define cada caracteristica fisica do corpo
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

    public void hitted(int value) {

        setActionLock(State.HIT, this.getDirection());
        this.animations.overrideAnimation(State.HIT, this.getDirection());

        this.health -= value;
        this.isHit = true;

        if (this.health <= 0 && !triggerDeath) {
            setActionLock(State.DYING, Direction.ALL);
            this.animations.overrideAnimation(State.DYING, Direction.ALL);
            triggerDeath = true;
        }
    }

    private void verifyDeath(float dt) {
        if (triggerDeath && this.animations.isCurrentAnimationFinished()) {
            dead = true;
            super.setSelfDestruct();
        }
    }

    public boolean isDead() {
        return dead;
    }

}
