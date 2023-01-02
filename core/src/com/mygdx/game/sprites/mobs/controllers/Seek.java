/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs.controllers;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class Seek implements MovimentController {

    private Mob thisMob;

    public Seek(Mob thisMob) {
        this.thisMob = thisMob;
    }

    @Override
    public void move(Mob mob, float dt) {

        Mob player = thisMob.getEntityHandler().getPlayer();

        float px = player.getBody().getWorldCenter().x;
        float py = player.getBody().getWorldCenter().y;

        float tx = thisMob.getBody().getWorldCenter().x;
        float ty = thisMob.getBody().getWorldCenter().y;

        int applyX = 0;
        int applyY = 0;

        int speedModifier = 10;

        if (px - tx > 0) {
            applyX = speedModifier;
        } else {
            applyX = -speedModifier;
        }

        if (py - ty > 0) {
            applyY = speedModifier;
        } else {
            applyY = -speedModifier;
        }

        thisMob.getBody().setLinearVelocity(applyX, applyY);

    }

}
