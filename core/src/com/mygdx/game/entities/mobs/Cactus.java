
package com.mygdx.game.entities.mobs;

import com.mygdx.game.constants.CollisionCategories;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.constants.GlobalConfig;
import com.mygdx.game.constants.Pair;
import com.mygdx.game.constants.State;
import com.mygdx.game.controllers.EntityHandler;
import com.mygdx.game.controllers.MapHandler;
import com.mygdx.game.sprites.AnimationHandler;
import com.mygdx.game.controllers.interfaces.MovimentController;
import com.mygdx.game.controllers.*;
import com.mygdx.game.controllers.SingleShot;
import java.util.HashMap;
import java.util.Map;


public class Cactus extends Mob {

    public Cactus(MapHandler mapHandler, EntityHandler entityHandler) {
        super(mapHandler, entityHandler, CollisionCategories.ENEMY_BODY,
                new short[]{
                    CollisionCategories.PLAYER_BODY,
                    CollisionCategories.PLAYER_PROJECTILE,
                    CollisionCategories.ENEMY_BODY,
                    CollisionCategories.WALL
                });

        this.setAttackDamage(GlobalConfig.CactusDamageModifier);
        this.setSpeedModifier(GlobalConfig.CactusSpeedModifier);
        this.setHealth(GlobalConfig.CactusHealth);


        this.mController = (MovimentController) new SeekAndAvoid(this);
        this.attackType = new SingleShot(this);

        attackType.setDelay(0.5f);

    }

    public Cactus(MapHandler mapHandler, EntityHandler entityHandler, float startX, float startY) {
        super(mapHandler, entityHandler, CollisionCategories.ENEMY_BODY,
                new short[]{
                    CollisionCategories.PLAYER_BODY,
                    CollisionCategories.PLAYER_PROJECTILE,
                    CollisionCategories.ENEMY_BODY,
                    CollisionCategories.WALL,},
                startX, startY
        );

        this.setAttackDamage(GlobalConfig.CactusDamageModifier);
        this.setSpeedModifier(GlobalConfig.CactusSpeedModifier);
        this.setHealth(GlobalConfig.CactusHealth);

        this.mController = (MovimentController) new SeekAndAvoid(this);
        this.attackType = new SingleShot(this);

        attackType.setDelay(0.5f);
        this.setHealth(2);
    }

    @Override
    protected void defineThisBody(float x, float y) {

        this.bodyW = 12;
        this.bodyH = 18;

        createBoxCollisionBody(bodyW / 2, bodyH / 2, BodyDef.BodyType.DynamicBody, x, y, 0.5f);

        //inicializa a sprite olhando pro sul
        lastDirection = Direction.S;

        //limites do desenho da sprite e tamanho
        super.boxYOffset = 2;
        super.setBounds(0, 0, 28, 28);

        //esses arrays a seguir sao usados para configurar o recurte e configuracao das anima��es
        String[] regions = new String[]{
            "Cactus Front Sheet",
            "Cactus Back Sheet",
            "Cactus Side Sheet"
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

        directionDictionary.put(Direction.N, new Pair<>("Cactus Back Sheet", false));
        directionDictionary.put(Direction.S, new Pair<>("Cactus Front Sheet", false));
        directionDictionary.put(Direction.E, new Pair<>("Cactus Side Sheet", false));
        directionDictionary.put(Direction.W, new Pair<>("Cactus Side Sheet", true));
        directionDictionary.put(Direction.ALL, new Pair<>("Death Explosion", true));

        animations = new AnimationHandler();
        animations.setDirectionDictionary(directionDictionary);
        animations.setStateDictionary(stateDictionaty);

        animations.buildAnimationsBySheet("Mobs/Cactus/Cactus Back Sheet.png", "Cactus Back Sheet", 40, 40, framesPerAction, modes);
        animations.buildAnimationsBySheet("Mobs/Cactus/Cactus Front Sheet.png", "Cactus Front Sheet", 40, 40, framesPerAction, modes);
        animations.buildAnimationsBySheet("Mobs/Cactus/Cactus Side Sheet.png", "Cactus Side Sheet", 40, 40, framesPerAction, modes);
        
        animations.buildAnimationsBySheet("Mobs/Cactus/Death Explosion.png", "Death Explosion", 50, 55, new int []{8}, new Animation.PlayMode[]{Animation.PlayMode.NORMAL});
    }

    @Override
    public Direction getDirection() {
        float xVel = super.body.getLinearVelocity().x / 3;
        float yVel = super.body.getLinearVelocity().y / 3;

        //System.out.println("Cactus " + xVel + " <> " + yVel);
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
        }
        //}

        return lastDirection;

    }

    @Override
    protected TextureRegion getFrame(float dt) {
        return animations.getKeyFrame(getState(), getDirection(), dt);
    }
}
