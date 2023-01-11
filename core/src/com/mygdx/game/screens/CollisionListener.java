/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.State;
import com.mygdx.game.sprites.*;
import com.mygdx.game.entities.mobs.*;
import com.mygdx.game.sprites.objects.Wall;

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

       // checkProjectileMobCollision(a, b);
        checkProjectileWallCollision(a, b);
        //checkMeleeMobCollision(a, b);
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

    private void checkProjectileMobCollision(Fixture a, Fixture b) {
        Fixture projectile;
        Fixture hit;

        if (a.getBody().getUserData() instanceof Projectile && b.getBody().getUserData() instanceof Mob) {
            projectile = a;
            hit = b;
        } else if (b.getBody().getUserData() instanceof Projectile && a.getBody().getUserData() instanceof Mob) {
            projectile = b;
            hit = a;
        } else {
            return;
        }
        this.entityHandler.addToBeRemoved((Entity) projectile.getBody().getUserData());

        Mob hitMob = (Mob) hit.getBody().getUserData();

        hitMob.hitted();
        hitMob.setActionLock(State.HIT, hitMob.getDirection());
    }

    private void checkProjectileWallCollision(Fixture a, Fixture b) {
        Fixture projectile;
        Fixture hit;

        if (a.getBody().getUserData() instanceof Projectile && b.getBody().getUserData() instanceof Wall) {
            projectile = a;
            hit = b;
        } else if (b.getBody().getUserData() instanceof Projectile && a.getBody().getUserData() instanceof Wall) {
            projectile = b;
            hit = a;
        } else {
            return;
        }
        this.entityHandler.addToBeRemoved((Entity) projectile.getBody().getUserData());
    }

     private void checkMeleeMobCollision(Fixture a, Fixture b) {
        Fixture hitbox;
        Fixture hit;

        if (a.getBody().getUserData() instanceof MeleeHitbox && b.getBody().getUserData() instanceof Mob) {
            hitbox = a;
            hit = b;
        } else if (b.getBody().getUserData() instanceof MeleeHitbox && a.getBody().getUserData() instanceof Mob) {
            hitbox = b;
            hit = a;
        } else {
            return;
        }
        Mob hitMob = (Mob) hit.getBody().getUserData();
        hitMob.hitted();

    }

}