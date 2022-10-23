/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Hugo
 */
public class BaseSprite extends Sprite {

    public World world;
    public Body body;

    ///talvez seja mais interessante deixar os metodos e atributos de animação para cada classe filha
    private TextureAtlas textures;
    private Array<Array<Animation>> animations;
    private Array<Animation> idle;
    private Array<Animation> run;
    private Array<Animation> shot;
    private Array<Animation> hit;
    private Array<Animation> die;
    private float timer;

    public enum State {
        IDLE, RUNNING, SHOTING, HIT, DYING;
    }

    public enum Direction {
        N, NE, E, SE, S, SW, W, NW
    }
    private Direction lastDirection;

    public BaseSprite(World world, Screen screen) {
        this.world = world;
        defineThisBody();
    }

    private void defineThisBody() {
        BodyDef bdef = new BodyDef();

        //cria a definição do corpo fisico
        bdef.position.set(64, 64);
        bdef.type = BodyDef.BodyType.DynamicBody;

        //adiciona o corpo ao mundo
        body = world.createBody(bdef);

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
            Animation.PlayMode.NORMAL,
            Animation.PlayMode.NORMAL,
            Animation.PlayMode.NORMAL
        };

        
        Array<Array<Animation>> anim = constructAnimations("player.atlas", 48, 44, regions, framesPerRegions, modes);

        this.animations = new Array<>(anim);

        /*
        this.idle = anim.get(0);
        this.run = anim.get(1);
        this.shot = anim.get(2);
        this.hit = anim.get(3);
        this.die = anim.get(4);
        */
         
       // constructAnimations();
    }

    private Array<Array<Animation>> constructAnimations(String atlasName, int frameW, int frameH, String[] regionNames, int[] framesPerAction, Animation.PlayMode[] modes) {

        //esse codigo ficou confuso mas achei util para ser reutilizado para outras sprites
        //que seguem o padrão onde cada .png apresenta uma orientação e cada linha uma ação
        //preferi por separar cada ação em um array especifico onde a orientação sera obtida por indexação
        //lembrando que muito dessas orientações sera necessario flipar o frame
        //oq sera feito por outro metodo
        textures = new TextureAtlas(atlasName);

        Array<TextureRegion> textureArray = new Array<>();

        //separa todas as texturas por nome fornecido via parametro
        for (String regionName : regionNames) {
            textureArray.add(textures.findRegion(regionName));
        }
        //inicializa um array de arrays com o total de posições de ações na spritesheet
        Array<Array<Animation>> animationSet = new Array<>();
        
        for (int i = 0; i < framesPerAction.length; i++) {
            animationSet.add(new Array<Animation>());
        }

        Array<TextureRegion> frames = new Array<>();
        //para cada região separada 
        for (TextureRegion tr : textureArray) {
            //para cada ação nas diferentes regiões 
            for (int i = 0; i < framesPerAction.length; i++) {
                //quebra a região em frames
                for (int j = 0; j < framesPerAction[i]; j++) {
                    frames.add(new TextureRegion(tr, j * frameW, i * frameH, frameW, frameH));
                }
                //o array mais externo de animações recebe esta animação
                //ele manterá em cada indice de si um array que diz respeito a mesma ação porem em diferentes posições
                animationSet.get(i).add(new Animation<>(0.2f, frames, modes[i]));
                frames.clear();
            }
        }
        return animationSet;
    }

    private void constructAnimations() {

        final int FRAME_W = 48;
        final int FRAME_H = 44;

        textures = new TextureAtlas("player.atlas");

        Array<TextureRegion> textureArray = new Array<>();

        textureArray.add(textures.findRegion("Player Front Sheet"));
        textureArray.add(textures.findRegion("Player Back Sheet"));
        textureArray.add(textures.findRegion("Player Side Sheet"));
        textureArray.add(textures.findRegion("Player Angle 1 Sheet"));
        textureArray.add(textures.findRegion("Player Angle 2 Sheet"));

        idle = new Array<>();
        run = new Array<>();
        shot = new Array<>();
        hit = new Array<>();
        die = new Array<>();

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //para cada direção
        //lembrando que muitas dessas sprites serão flipadas
        for (TextureRegion tr : textureArray) {
            //montando animação de idle
            for (int i = 0; i < 6; i++) {

                frames.add(new TextureRegion(tr, i * FRAME_W, FRAME_H * 0, FRAME_W, FRAME_H));
            }

            idle.add(new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP));
            frames.clear();

            //montando animação de run
            for (int i = 0; i < 8; i++) {
                frames.add(new TextureRegion(tr, i * FRAME_W, FRAME_H * 1, FRAME_W, FRAME_H));
            }
            run.add(new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP));
            frames.clear();

            //montando animação de shot
            for (int i = 0; i < 6; i++) {
                frames.add(new TextureRegion(tr, i * FRAME_W, FRAME_H * 2, FRAME_W, FRAME_H));
            }
            shot.add(new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL));
            frames.clear();

            //montando animação de hit
            for (int i = 0; i < 1; i++) {
                frames.add(new TextureRegion(tr, i * FRAME_W, FRAME_H * 3, FRAME_W, FRAME_H));
            }
            hit.add(new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL));
            frames.clear();

            //montando animação de die
            for (int i = 0; i < 14; i++) {
                frames.add(new TextureRegion(tr, i * FRAME_W, FRAME_H * 3, FRAME_W, FRAME_H));
            }
            die.add(new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL));
            frames.clear();
        }
    }

    private TextureRegion getFrame(float dt) {

        int currentState = getState().ordinal();
        Direction currentDirection = getDirection();

        TextureRegion frame;

        timer = currentDirection == lastDirection ? timer + dt : 0;

        switch (getDirection()) {
            case N:
                frame = (TextureRegion) animations.get(currentState).get(1).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(1).getKeyFrame(timer);
                break;
            case NE:
                frame = (TextureRegion) animations.get(currentState).get(4).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(4).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case E:
                frame = (TextureRegion) animations.get(currentState).get(2).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(2).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case SE:
                frame = (TextureRegion) animations.get(currentState).get(3).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(3).getKeyFrame(timer);
                if (frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case S:
                frame = (TextureRegion) animations.get(currentState).get(0).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(0).getKeyFrame(timer);
                break;
            case SW:
                frame = (TextureRegion) animations.get(currentState).get(3).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(3).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case W:
                frame = (TextureRegion) animations.get(currentState).get(2).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(2).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            case NW:
                frame = (TextureRegion) animations.get(currentState).get(4).getKeyFrame(timer);
                //frame = (TextureRegion) run.get(4).getKeyFrame(timer);
                if (!frame.isFlipX()) {
                    frame.flip(true, false);
                }
                break;
            default:
                throw new AssertionError();
        }

        return frame;

    }

    private State getState() {
        return State.RUNNING;
    }

    private Direction getDirection() {
        
        float xVel = body.getLinearVelocity().x;
        float yVel = body.getLinearVelocity().y;
        float limit = 1f;

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

    public void update(float f) {
        super.setPosition(body.getPosition().x - super.getWidth() / 2, body.getPosition().y - super.getHeight() / 2);
        super.setRegion(getFrame(f));
    }

}
