/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.interfaces.AttackType;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.*;
import com.mygdx.game.entities.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class Melee implements AttackType {

    private float timer = 0;
    private float cooldown = 0;
    private float delay = 0;
    private int speedModifier = 100;
    private boolean actionLock = false;
    private boolean didShoot = false;
    private Direction lockedDirecion;
    private State lockedState;

    private MapHandler mh;
    private EntityHandler eh;

    private Mob thisMob;

    public Melee(Mob thisMob) {
        this.thisMob = thisMob;
        this.mh = thisMob.getMapHandler();
        this.eh = thisMob.getEntityHandler();
    }

    @Override
    public void act(float dt) {

        if (actionLock) {
            //esse codigo ta horrivel mas foi uma forma que encontrei de adicioanr delay a acao
            if (timer > delay && !didShoot) {
                didShoot = true;
                //System.out.println("Attacking");
                //System.out.println("Source Mob " + thisMob);
                new MeleeHitbox(mh, eh, this.thisMob, coordinates());
            }

            waitActionUnlock(dt);

        } else {
            //essa verificacao vai dentro do moviment controller
            //ela esta aqui por teste
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

    private Vector2 coordinates() {

        Direction d = lockedDirecion;


        float mobX = thisMob.getBody().getWorldCenter().x;
        float mobY = thisMob.getBody().getWorldCenter().y;
        float mobW = thisMob.getBodyW();
        float mobH = thisMob.getBodyH();

        float x = mobX;
        float y = mobY;

        if (d == Direction.N) {
            y = mobY + mobH/2;
        } else if (d == Direction.NE) {
            x = mobX + mobW/2;
            y = mobY + mobH/2;
        } else if (d == Direction.E) {
            x = mobX + mobW/2;
        } else if (d == Direction.SE) {
            x = mobX + mobW/2;
            y = mobY -mobH/2;
        } else if (d == Direction.S) {
            y = mobY - mobH/2;
        } else if (d == Direction.SW) {
            x = mobX - mobW/2;
            y = mobY - mobH/2;
        } else if (d == Direction.W) {
            x = mobX - mobW/2;
        } else if (d == Direction.NW) {
            x = mobX - mobW/2;
            y = mobY + mobH/2;
        }
        return new Vector2(x, y);

    }

    public void attack(State state, Direction direction) {

        if (!actionLock && lockedDirecion == null && lockedState == null) {
            //System.out.println("Locked Single-Shot");
            this.lockedState = state;
            this.lockedDirecion = direction;
            thisMob.getAnimations().setAnimationLock(state, direction);
            this.actionLock = true;
            thisMob.setState(state);
        }

    }

    private void waitActionUnlock(float dt) {
        if (this.actionLock && thisMob.getAnimations().isCurrentAnimationFinished() && timer > cooldown) {
            //System.out.println("Unlocked Single-Shot");
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

    @Override
    public void setAttackModifier(float f) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public float getAttackMoidifier() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
