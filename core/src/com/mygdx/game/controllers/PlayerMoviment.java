
package com.mygdx.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.constants.GlobalConfig;

import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.entities.mobs.Mob;

public class PlayerMoviment implements MovimentController {

    @Override
    public void move(Mob mob, float dt) {

        float applyX = 0;
        float applyY = 0;

        float mobVX = mob.getBody().getLinearVelocity().x;
        float mobVY = mob.getBody().getLinearVelocity().y;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            applyY += GlobalConfig.PlayerSpeedModifier;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            applyY += -GlobalConfig.PlayerSpeedModifier;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            applyX += GlobalConfig.PlayerSpeedModifier;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            applyX += -GlobalConfig.PlayerSpeedModifier;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mob.getAttackType().attack(State.SHOTING, mob.getDirection());
        }
        mob.getBody().setLinearVelocity(applyX, applyY);
    }
}
