
package com.mygdx.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.interfaces.AttackType;
import com.mygdx.game.sprites.Projectile;
import com.mygdx.game.entities.mobs.Mob;

import java.util.ArrayList;
import java.util.List;

public class SingleShot implements AttackType {

    private float timer = 0;
    private float cooldown = 0;
    private float delay = 0;
    private float speedModifier = 1000000;
    private boolean actionLock = false;
    private boolean didShoot = false;
    private Direction lockedDirecion;
    private State lockedState;

    private MapHandler mh;
    private EntityHandler eh;

    private Mob thisMob;

    private List<Projectile> bullets = new ArrayList<>();

    public SingleShot(Mob thisMob) {
        this.thisMob = thisMob;
        this.mh = thisMob.getMapHandler();
        this.eh = thisMob.getEntityHandler();
    }

    @Override
    public void act(float dt) {
        if (actionLock) {
            if (timer > delay && !didShoot) {
                didShoot = true;
                new Projectile(mh, eh, this.thisMob, getProjectileDirection());
            }
            waitActionUnlock(dt);
        }
    }

    @Override
    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public boolean isAttacking() {
        return thisMob.getAnimations().isCurrentAnimationFinished();
    }

    private Vector2 getProjectileDirection() {

        Direction d = lockedDirecion;
        float x = 0;
        float y = 0;

        if (d == Direction.N) {
            y = speedModifier;
        } else if (d == Direction.NE) {
            x = y = speedModifier;
        } else if (d == Direction.E) {
            x = speedModifier;
        } else if (d == Direction.SE) {
            y = -speedModifier;
            x = speedModifier;
        } else if (d == Direction.S) {
            y = -speedModifier;
        } else if (d == Direction.SW) {
            x = y = -speedModifier;
        } else if (d == Direction.W) {
            x = -speedModifier;
        } else if (d == Direction.NW) {
            y = speedModifier;
            x = -speedModifier;
        }
        return new Vector2(x, y);

    }

    public void attack(State state, Direction direction) {

        if (!actionLock && lockedDirecion == null && lockedState == null) {
            this.lockedState = state;
            this.lockedDirecion = direction;
            thisMob.getAnimations().setAnimationLock(state, direction);
            this.actionLock = true;
            thisMob.setState(state);
        }

    }

    private void waitActionUnlock(float dt) {
        if (this.actionLock && thisMob.getAnimations().isCurrentAnimationFinished() && timer > cooldown) {
            this.lockedState = null;
            this.lockedDirecion = null;
            this.actionLock = false;
            this.timer = 0;
            this.didShoot = false;

        }
        timer += dt;
    }
    @Override
    public void setDelay(float f) {
        this.delay = f;
    }
}
