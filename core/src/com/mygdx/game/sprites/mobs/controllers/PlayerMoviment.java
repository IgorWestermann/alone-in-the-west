/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Direction;

import com.mygdx.game.constants.State;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.Projectile;
import com.mygdx.game.sprites.mobs.Mob;
import com.mygdx.game.sprites.mobs.controllers.MovimentController;

/**
 *
 * @author Hugo
 */
public class PlayerMoviment implements MovimentController {

    @Override
    public void move(Mob mob, float dt) {

        float applyX = 0;
        float applyY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            applyY = 50;

        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            applyY = -50;
        } else {
            applyY = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            applyX = 50;

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            applyX = -50;

        } else {
            applyX = 0;
        }

        float mobVX = mob.getBody().getLinearVelocity().x;
        float mobVY = mob.getBody().getLinearVelocity().y;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mob.getAttackType().attack(State.SHOTING, mob.getDirection());
            System.out.println(mob.getAttackType().getAttackMoidifier());
        }

        mob.getBody().setLinearVelocity(applyX, applyY);

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.out.println("E");
            mob.getBody().applyLinearImpulse(10000 * mobVX, 10000 * mobVY, 0, 0, true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            System.out.println("P");
            
            float pSpeed = mob.getAttackType().getAttackMoidifier();
            mob.getAttackType().setAttackModifier(pSpeed + 20);
        }

    }

}
