
package com.mygdx.game.controllers;

import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.GlobalConfig;
import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.entities.mobs.Mob;

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

        float maxDistance = 70;
        float offset = 20;

        int modifier = GlobalConfig.CactusSpeedModifier;
        int limit = GlobalConfig.SpeedLimit;

        double squareDistance = ((px - tx) * (px - tx)) + ((py - ty) * (py - ty));

        if (Math.abs(px - tx) < maxDistance - offset && Math.abs(py - ty) < maxDistance - offset) {
            //foge
            //verifica a menor distancia
            if (Math.abs((px - tx)) > Math.abs(py - ty)) {
                //se modulo de x maior  
                //foge pra y
                if (py - ty > 0) {
                    applyY += -modifier;
                }
                if (py - ty < 0) {
                    applyY += modifier;
                }
            } else {
                //se modulo de y maior
                //foge pra x
                if (px - tx > 0) {
                    applyX += -modifier;
                }
                if (px - tx < 0) {
                    applyX += modifier;
                }
            }
        } else if (Math.abs(px - tx) > maxDistance + offset || Math.abs(py - ty) > maxDistance + offset) {
            //se esta fora da distancia de fuga

            if (Math.abs((px - tx)) > Math.abs(py - ty)) {
                //se modulo de x maior  
                //persegue x
                if (px - tx > 0) {
                    applyX += modifier;
                }
                if (px - tx < 0) {
                    applyX += -modifier;
                }
            } else {
                //se modulo de y maior
                //persegue y
                if (py - ty > 0) {
                    applyY += modifier;
                }
                if (py - ty < 0) {
                    applyY += -modifier;
                }
            }
        } else {
            //verifica a menor distancia
            if (Math.abs((px - tx)) > Math.abs(py - ty)) {
                //alinha em y
                if (py - ty > 0) {
                    applyY += modifier;
                }
                if (py - ty < 0) {
                    applyY += -modifier;
                }
            } else {
                //alinha em x
                if (px - tx > 0) {
                    applyX += modifier;
                }
                if (px - tx < 0) {
                    applyX += -modifier;
                }
            }
        }

        //atira se estiver dentro da distancia e alinhado
        if ((Math.abs(px - tx) < offset || Math.abs(py - ty) < offset)) {

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
