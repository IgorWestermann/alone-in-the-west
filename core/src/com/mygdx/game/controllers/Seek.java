
package com.mygdx.game.controllers;

import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.entities.mobs.Mob;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.State;

public class Seek implements MovimentController {

    private Mob thisMob;

    public Seek(Mob thisMob) {
        this.thisMob = thisMob;
    }

    @Override
    public void move(Mob mob, float dt) {

        Mob player = thisMob.getEntityHandler().getPlayer();

        int px = (int) player.getBody().getWorldCenter().x;
        int py = (int) player.getBody().getWorldCenter().y;

        int tx = (int) thisMob.getBody().getWorldCenter().x;
        int ty = (int) thisMob.getBody().getWorldCenter().y;

        int applyX = 0;
        int applyY = 0;

        int speedModifier = 10;

        if (px - tx > 5) {
            applyX = speedModifier;
        } else if (px - tx < -5) {
            applyX = -speedModifier;
        }

        if (py - ty > 5) {
            applyY = speedModifier;
        } else if (py - ty < -5) {
            applyY = -speedModifier;
        }

        double squaredDistance = (((px - tx) * (px - tx)) + ((py - ty) * (py - ty)));

        if (squaredDistance < thisMob.getBodyH() * thisMob.getBodyH()) {
            this.thisMob.getAttackType().attack(State.SHOTING, getPlayerDirection());
        }

        thisMob.getBody().setLinearVelocity(applyX, applyY);

    }

    private Direction getPlayerDirection() {

        Mob player = thisMob.getEntityHandler().getPlayer();

        float px = player.getBody().getWorldCenter().x;
        float py = player.getBody().getWorldCenter().y;

        float tx = thisMob.getBody().getWorldCenter().x;
        float ty = thisMob.getBody().getWorldCenter().y;

        //verifica pra onde a distancia � menor
        if (Math.abs((px - tx)) < Math.abs(py - ty)) {
            //modulo de y menor
            if (py - ty > 0) {
                //o player esta acima do mob
                return Direction.N;
            } else {
                //o player esta abaixo do mob
                return Direction.S;
            }
        } else {
            if (px - tx > 0) {
                //o player esta a direita do mob
                return Direction.E;
            } else {
                //o player esta a esquerda do mob
                return Direction.W;
            }
        }
    }

}
