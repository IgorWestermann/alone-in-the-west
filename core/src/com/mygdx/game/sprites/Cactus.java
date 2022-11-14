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
public class Cactus extends Entity {

    public enum State {
        IDLE, RUNNING, SHOTING, HIT, DYING;
    }

    public enum Direction {
        N, E, S, W,
    }
    Direction lastDirection;

    public Cactus(World world, Screen screen) {
        super(world, screen);
    }

    @Override
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
        lastDirection = Cactus.Direction.S;

        //limites do desenho da sprite e tamanho
        super.setBounds(0, 0, 32, 32);

        //esses arrays a seguir são usados para configurar o recurte e configuração das animações
        String[] regions = new String[]{
            "Cactus Back Sheet",
            "Cactus Front Sheet",
            "Cactus Side Sheet"
        };

        int[] framesPerAction = new int[]{
            4, 9, 1, 1
        };

        Animation.PlayMode[] modes = new Animation.PlayMode[]{
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,
            Animation.PlayMode.LOOP,};

        Array<Array<Animation>> anim = super.constructAnimations("Mobs/Cactus.pack",
                40,
                41,
                regions,
                framesPerAction,
                modes);
        super.animations = new Array<>(anim);
    }
    private Direction getDirection() {
        float xVel = super.body.getLinearVelocity().x;
        float yVel = super.body.getLinearVelocity().y;

        if (xVel > 0) {
            lastDirection = Cactus.Direction.W;
            return Cactus.Direction.W;

        } else if (xVel < 0) {
            lastDirection = Cactus.Direction.E;
            return Cactus.Direction.E;
        } else if (yVel > 0) {
            lastDirection = Cactus.Direction.N;
            return Cactus.Direction.N;

        } else if (yVel < 0) {
            lastDirection = Cactus.Direction.S;
            return Cactus.Direction.S;

        } else {
            return lastDirection;
        }
    }
    
    private State getState(){
        
        return Cactus.State.RUNNING;
    }

    @Override
    protected TextureRegion getFrame(float dt) {

        int currentState = getState().ordinal();
        Cactus.Direction currentDirection = getDirection();

        TextureRegion frame;
        timer = currentDirection == lastDirection ? timer + dt : 0;

        switch (getDirection()) {
            case N:
                frame = (TextureRegion) animations.get(currentState).get(1).getKeyFrame(timer);
                break;
            case E:
                frame = (TextureRegion) animations.get(currentState).get(0).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case S:
                frame = (TextureRegion) animations.get(currentState).get(2).getKeyFrame(timer);
                break;
            case W:
                frame = (TextureRegion) animations.get(currentState).get(0).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            default:
                throw new AssertionError();
        }
        return frame;
    }

}
