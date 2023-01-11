
package com.mygdx.game.entities.mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.constants.*;
import com.mygdx.game.controllers.EntityHandler;
import com.mygdx.game.controllers.MapHandler;
import com.mygdx.game.sprites.AnimationHandler;
import com.mygdx.game.constants.CollisionCategories;
import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.controllers.*;
import java.util.HashMap;
import java.util.Map;


public class Coffin extends Mob {

    public Coffin(MapHandler mapHandler, EntityHandler entityHandler) {
        super(mapHandler, entityHandler, CollisionCategories.ENEMY_BODY,
                new short[]{
                    CollisionCategories.ENEMY_BODY,
                    CollisionCategories.PLAYER_BODY,
                    CollisionCategories.PLAYER_PROJECTILE,
                    CollisionCategories.WALL,}, 8, 12
        );

        this.setAttackDamage(GlobalConfig.CoffinDamageModifier);
        this.setSpeedModifier(GlobalConfig.CoffinSpeedModifier);
        this.setHealth(GlobalConfig.CoffinHealth);

        this.mController = (MovimentController) new Seek(this);
        this.attackType  = new SingleShot(this);
        attackType.setDelay(0.5f);
    }

    public Coffin(MapHandler mapHandler, EntityHandler entityHandler, float startX, float startY) {
        super(mapHandler, entityHandler, CollisionCategories.ENEMY_BODY,
                new short[]{
                    CollisionCategories.ENEMY_BODY,
                    CollisionCategories.PLAYER_BODY,
                    CollisionCategories.PLAYER_PROJECTILE,
                    CollisionCategories.WALL,},
                startX, startY);

        this.mController = (MovimentController) new Seek(this);
        this.attackType = new Melee(this);
        attackType.setDelay(0.5f);
    }

    @Override
    protected void defineThisBody(float x, float y) {

        this.bodyW = 16;
        this.bodyH = 24;

        createBoxCollisionBody(bodyW / 2, bodyH / 2, BodyDef.BodyType.DynamicBody, x, y, 0.5f);

        //inicializa a sprite olhando pro sul
        lastDirection = Direction.S;

        //limites do desenho da sprite e tamanho
        //super.boxXOffset = 5;
        //super.boxYOffset = -1;
        super.setBounds(0, 0, 44, 44);

        //esses arrays a seguir sao usados para configurar o recurte e configuracao das animacoes
        String[] sprites = new String[]{
            "Coffin Front Sheet",
            "Coffin Back Sheet",
            "Coffin Side Sheet"
        };

        int[] framesPerAction = new int[]{
            4, 9, 11, 1
        };

        Animation.PlayMode[] modes = new Animation.PlayMode[]{
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,};

        Map<State, Integer> stateDictionaty = new HashMap<>();
        stateDictionaty.put(State.IDLE, 0);
        stateDictionaty.put(State.RUNNING, 1);
        stateDictionaty.put(State.SHOTING, 2);
        stateDictionaty.put(State.HIT, 3);
        stateDictionaty.put(State.DYING, 0);

        Map<Direction, Pair<String, Boolean>> directionDictionary = new HashMap<>();

        directionDictionary.put(Direction.N, new Pair<>("Coffin Back Sheet", false));
        directionDictionary.put(Direction.S, new Pair<>("Coffin Front Sheet", false));
        directionDictionary.put(Direction.E, new Pair<>("Coffin Side Sheet", false));
        directionDictionary.put(Direction.W, new Pair<>("Coffin Side Sheet", true));
         directionDictionary.put(Direction.ALL, new Pair<>("Death Explosion", true));

        animations = new AnimationHandler();
        animations.buildAnimationsBySpriteList("Mobs/Coffin/", sprites, 74, 70, framesPerAction, modes);
        animations.buildAnimationsBySheet("Mobs/Coffin/Death Explosion.png", "Death Explosion", 50, 55, new int []{8}, new Animation.PlayMode[]{Animation.PlayMode.NORMAL});
        animations.setDirectionDictionary(directionDictionary);
        animations.setStateDictionary(stateDictionaty);
    }

    @Override
    public Direction getDirection() {
        float xVel = super.body.getLinearVelocity().x;
        float yVel = super.body.getLinearVelocity().y;

        if (xVel > 0) {
            lastDirection = Direction.E;
            return Direction.E;

        } else if (xVel < 0) {
            lastDirection = Direction.W;
            return Direction.W;
        } else if (yVel > 0) {
            lastDirection = Direction.N;
            return Direction.N;

        } else if (yVel < 0) {
            lastDirection = Direction.S;
            return Direction.S;

        } else {
            return lastDirection;
        }
    }

    @Override
    protected TextureRegion getFrame(float dt) {
        return animations.getKeyFrame(getState(), getDirection(), dt);
    }
}
