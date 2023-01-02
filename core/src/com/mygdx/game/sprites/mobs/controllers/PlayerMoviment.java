/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

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
        
        mob.getBody().setLinearVelocity(applyX, applyY);

    }

}
