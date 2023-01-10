/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.controllers;

import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.entities.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class SeekAndAvoid implements MovimentController {

    private Mob thisMob;

    public SeekAndAvoid(Mob thisMob) {
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

        int speedModifier = 40;

        float maxDistance = 50;

        System.out.println(Math.abs(px - tx));

        //fogr
        if (Math.abs(px - tx) < maxDistance - 10 && Math.abs(py - ty) < maxDistance - 10 ) {
            if (px - tx > 0) {
                applyX = -speedModifier;
            } else {
                applyX = speedModifier;
            }

            if (py - ty > 0) {
                applyY = -speedModifier;
            } else {
                applyY = speedModifier;
            }
            //persegue
        } else if (Math.abs(px - tx) > maxDistance  + 10 && Math.abs(py - ty) < maxDistance + 10) {

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
        }
        thisMob.getBody().setLinearVelocity(applyX, applyY);

    }

}
