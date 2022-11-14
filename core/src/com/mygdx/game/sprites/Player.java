/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Hugo
 */
public class Player extends Entity {

    public enum State {
        IDLE, RUNNING, SHOTING, HIT, DYING;
    }

    public enum Direction {
        N, NE, E, SE, S, SW, W, NW
    }
    Direction lastDirection;

    public Player(World world, Screen screen) {
        super(world, screen);
    }

    private Direction getDirection() {

        float xVel = super.body.getLinearVelocity().x;
        float yVel = super.body.getLinearVelocity().y;

        float difference = Math.abs(Math.abs(xVel) - Math.abs(yVel));

        float limit = 0.2f;

        if (Math.abs(xVel) <= limit && yVel > limit) {
            //norte
            lastDirection = Direction.N;
            return Direction.N;
        } else if (xVel > limit && yVel > limit) {
            //nordeste
            lastDirection = Direction.NE;
            return Direction.NE;
        } else if (xVel > limit && Math.abs(yVel) <= limit) {
            //leste
            lastDirection = Direction.E;
            return Direction.E;
        } else if (xVel > limit && yVel < -limit) {
            //sudeste
            lastDirection = Direction.SE;
            return Direction.SE;
        } else if (Math.abs(xVel) <= limit && yVel < -limit) {
            //sul
            lastDirection = Direction.S;
            return Direction.S;
        } else if (xVel < -limit && yVel < -limit) {
            //sudoeste
            lastDirection = Direction.SW;
            return Direction.SW;
        } else if (xVel < -limit && Math.abs(yVel) <= limit) {
            //oeste
            lastDirection = Direction.W;
            return Direction.W;
        } else if (xVel < -limit && yVel > limit) {
            //noroeste
            lastDirection = Direction.NW;
            return Direction.NW;
        } else {
            return lastDirection;
        }

    }

    private State getState() {
        return State.DYING;
    }

    //toda entity implementa esse metodo para definir sua propria regra de animação
    @Override
    protected TextureRegion getFrame(float dt) {

        int currentState = getState().ordinal();
        Direction currentDirection = getDirection();

        TextureRegion frame;
        timer = currentDirection == lastDirection ? timer + dt : 0;

        switch (getDirection()) {
            case N:
                frame = (TextureRegion) animations.get(currentState).get(1).getKeyFrame(timer);
                break;
            case NE:
                frame = (TextureRegion) animations.get(currentState).get(4).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case E:
                frame = (TextureRegion) animations.get(currentState).get(2).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case SE:
                frame = (TextureRegion) animations.get(currentState).get(3).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case S:
                frame = (TextureRegion) animations.get(currentState).get(0).getKeyFrame(timer);
                break;
            case SW:
                frame = (TextureRegion) animations.get(currentState).get(3).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case W:
                frame = (TextureRegion) animations.get(currentState).get(2).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case NW:
                frame = (TextureRegion) animations.get(currentState).get(4).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            default:
                throw new AssertionError();
        }
        return frame;
    }

    @Override
    //todo sprite implementa essa função para definir seu priprio corpo e animação
    protected void defineThisBody() {
        BodyDef bdef = new BodyDef();
        //cria a definição do corpo fisico
        bdef.position.set(64, 64);
        bdef.type = BodyDef.BodyType.DynamicBody;

        //adiciona o corpo ao mundo
        super.body = super.world.createBody(bdef);

        //definições de fixture e formato
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //o tamanho do shape é settado do centro em varias direções a partir de um raio
        //assim , o tamanho dos parametros é metade do tamanho da sprite
        shape.setAsBox(6, 8);

        fdef.shape = shape;
        body.createFixture(fdef);

        //inicializa a sprite olhando pro sul
        lastDirection = Direction.S;

        //limites do desenho da sprite e tamanho
        super.setBounds(0, 0, 32, 32);

        //esses arrays a seguir são usados para configurar o recurte e configuração das animações
        String[] regions = new String[]{"Player Front Sheet",
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
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP
        };
        
        Array<Array<Animation>> anim = super.constructAnimations("Player/Player.pack",
                48,
                44,
                regions,
                framesPerRegions,
                modes);
        super.animations = new Array<>(anim);
    }

}
