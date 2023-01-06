/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs;

import com.mygdx.game.sprites.CollisionCategories;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.constants.*;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.AnimationHandler;
import com.mygdx.game.sprites.mobs.controllers.MovimentController;
import com.mygdx.game.sprites.mobs.controllers.PlayerMoviment;
import com.mygdx.game.sprites.mobs.controllers.Seek;
import com.mygdx.game.sprites.mobs.controllers.SingleShot;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Hugo
 */
public class Player extends Mob {

    public Player(MapHandler mapHandler, EntityHandler entityHandler) {
        super(mapHandler, entityHandler, CollisionCategories.PLAYER_BODY,
                new short[]{
                    CollisionCategories.ENEMY_BODY,
                    CollisionCategories.PLAYER_BODY,
                    CollisionCategories.ENEMY_PROJECTILE,
                    CollisionCategories.WALL,
                });

        this.mController = new PlayerMoviment();
        this.attackType = new SingleShot(this);
    }

    public Player(MapHandler mapHandler, EntityHandler entityHandler, float startX, float startY) {
        super(mapHandler, entityHandler, CollisionCategories.PLAYER_BODY, new short[]{
            CollisionCategories.ENEMY_BODY,
            CollisionCategories.PLAYER_BODY,
            CollisionCategories.ENEMY_PROJECTILE,
            CollisionCategories.WALL,}, startX, startY);

        this.mController = new PlayerMoviment();
        this.attackType = new SingleShot(this);
    }

    @Override
    public Direction getDirection() {

        float xVel = super.body.getLinearVelocity().x;
        float yVel = super.body.getLinearVelocity().y;

        if (xVel == 0 && yVel > 0) {
            //norte
            lastDirection = Direction.N;
            return Direction.N;
        } else if (xVel > 0 && yVel > 0) {
            //nordeste
            lastDirection = Direction.NE;
            return Direction.NE;
        } else if (xVel > 0 && yVel == 0) {
            //leste
            lastDirection = Direction.E;
            return Direction.E;
        } else if (xVel > 0 && yVel < 0) {
            //sudeste
            lastDirection = Direction.SE;
            return Direction.SE;
        } else if (xVel == 0 & yVel < 0) {
            //sul
            lastDirection = Direction.S;
            return Direction.S;
        } else if (xVel < 0 && yVel < 0) {
            //sudoeste
            lastDirection = Direction.SW;
            return Direction.SW;
        } else if (xVel < 0 && yVel == 0) {
            //oeste
            lastDirection = Direction.W;
            return Direction.W;
        } else if (xVel < 0 && yVel > 0) {
            //noroeste
            lastDirection = Direction.NW;
            return Direction.NW;
        } else {
            return lastDirection;
        }

    }

    //toda entity implementa esse metodo para definir sua propria regra de animacao
    @Override
    protected TextureRegion getFrame(float dt) {
        return animations.getKeyFrame(getState(), getDirection(), dt);
    }

    //todo sprite implementa essa funcao para definir seu priprio corpo e anima��o
    @Override
    protected void defineThisBody(float startX, float startY) {

        createBoxCollisionBody(6, 11, BodyDef.BodyType.DynamicBody, startX, startX, 0.5f);

        //inicializa a sprite olhando pro sul
        lastDirection = Direction.S;

        //limites do desenho da sprite e tamanho
        super.boxXOffset = 0.5f;
        super.boxYOffset = 2;
        super.setBounds(0, 0, 32, 32);

        //esses arrays a seguir sao usados para configurar o recorte e configuracao das animacoes
        String[] sprites = new String[]{
            "Player Front Sheet",
            "Player Back Sheet",
            "Player Side Sheet",
            "Player Angle 1 Sheet",
            "Player Angle 2 Sheet"
        };

        int[] framesPerRegions = new int[]{
            6, 8, 6, 1, 14
        };

        Animation.PlayMode[] modes = new Animation.PlayMode[]{
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,
            Animation.PlayMode.NORMAL,
            Animation.PlayMode.NORMAL,
            Animation.PlayMode.NORMAL
        };

        Map<State, Integer> stateDictionaty = new HashMap<>();
        stateDictionaty.put(State.IDLE, 0);
        stateDictionaty.put(State.RUNNING, 1);
        stateDictionaty.put(State.SHOTING, 2);
        stateDictionaty.put(State.HIT, 3);
        stateDictionaty.put(State.DYING, 4);

        Map<Direction, Pair<String, Boolean>> directionDictionary = new HashMap<>();
        directionDictionary.put(Direction.S, new Pair<>("Player Front Sheet", false));
        directionDictionary.put(Direction.SW, new Pair<>("Player Angle 1 Sheet", true));
        directionDictionary.put(Direction.W, new Pair<>("Player Side Sheet", true));
        directionDictionary.put(Direction.NW, new Pair<>("Player Angle 2 Sheet", true));
        directionDictionary.put(Direction.N, new Pair<>("Player Back Sheet", false));
        directionDictionary.put(Direction.NE, new Pair<>("Player Angle 2 Sheet", false));
        directionDictionary.put(Direction.E, new Pair<>("Player Side Sheet", false));
        directionDictionary.put(Direction.SE, new Pair<>("Player Angle 1 Sheet", false));

        animations = new AnimationHandler();
        animations.buildAnimationsBySpriteList("Player/", sprites, 48, 44, framesPerRegions, modes);
        animations.setStateDictionary(stateDictionaty);
        animations.setDirectionDictionary(directionDictionary);
    }
}
