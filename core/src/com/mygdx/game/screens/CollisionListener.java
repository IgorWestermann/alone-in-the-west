/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.State;
import com.mygdx.game.sprites.*;
import com.mygdx.game.sprites.mobs.*;


/**
 *
 * @author Hugo
 */
public class CollisionListener implements ContactListener {

    private MapHandler mapHandler;
    private EntityHandler entityHandler;

    public CollisionListener(MapHandler mapHandler, EntityHandler entityHandler) {
        this.mapHandler = mapHandler;
        this.entityHandler = entityHandler;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        checkProjectileCollision(a, b);
    }

    @Override
    public void endContact(Contact cntct) {

    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {

    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {

    }

    private void checkProjectileCollision(Fixture a, Fixture b) {
        Fixture projectile;
        Fixture hit;

        if (a.getBody().getUserData() instanceof Projectile && b.getBody().getUserData() instanceof Mob) {
            projectile = a;
            hit = b;
        } else if (b.getBody().getUserData() instanceof Projectile &&  a.getBody().getUserData() instanceof Mob) {
            projectile = b;
            hit = a;
            ((Mob) a.getBody().getUserData()).setHealth(((Mob) a.getBody().getUserData()).getHealth() -1);
        } else {
            return;
        }

        System.out.println("Hit a ->" + hit.getBody().getUserData().toString()
                + " --- "
                + ((Mob) a.getBody().getUserData()).getHealth()
        );

        this.entityHandler.addToBeRemoved((Entity)projectile.getBody().getUserData());

        Mob hitMob = (Mob)hit.getBody().getUserData();
        hitMob.setActionLock(State.HIT, hitMob.getDirection());
    }

}