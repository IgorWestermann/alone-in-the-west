/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.GlobalConfig;

import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.entities.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class PlayerMoviment implements MovimentController {

    @Override
    public void move(Mob mob, float dt) {

        float applyX = 0;
        float applyY = 0;

        float mobVX = mob.getBody().getLinearVelocity().x;
        float mobVY = mob.getBody().getLinearVelocity().y;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && mobVY < GlobalConfig.SpeedLimit) {
            applyY += GlobalConfig.PlayerSpeedModifier;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && mobVY > - GlobalConfig.SpeedLimit) {
            applyY = -GlobalConfig.PlayerSpeedModifier;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && mobVX < GlobalConfig.SpeedLimit) {
            applyX = GlobalConfig.PlayerSpeedModifier;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && mobVX > - GlobalConfig.SpeedLimit) {
            applyX = -GlobalConfig.PlayerSpeedModifier;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mob.getAttackType().attack(State.SHOTING, mob.getDirection());
        }

        mob.getBody().setLinearVelocity(applyX, applyY);

    }
}
