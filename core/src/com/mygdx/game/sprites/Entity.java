/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
/**
 *
 * @author Hugo
 */
public abstract class Entity extends Sprite {

    public World world;
    public Body body;
    ///talvez seja mais interessante deixar os metodos e atributos de anima��o para cada classe filha
    private TextureAtlas textures;
    protected Array<Array<Animation>> animations;
    float timer;

    public Entity(World world, Screen screen) {
        this.world = world;
        defineThisBody();
    }

    protected abstract void defineThisBody();

    protected abstract TextureRegion getFrame(float dt);

    protected Array<Array<Animation>> constructAnimations(String atlasName, int frameW, int frameH, String[] regionNames, int[] framesPerAction, Animation.PlayMode[] modes) {
        //esse codigo ficou confuso mas achei util para ser reutilizado para outras sprites
        //que seguem o padr�o onde cada .png apresenta uma orienta��o e cada linha uma a��o
        //preferi por separar cada a��o em um array especifico onde a orienta��o sera obtida por indexa��o
        //lembrando que muito dessas orienta��es sera necessario flipar o frame
        //oq sera feito por outro metodo
        
        textures = new TextureAtlas(atlasName);
        Array<TextureRegion> textureArray = new Array<>();

        //separa todas as texturas por nome fornecido via parametro
        for (String regionName : regionNames) {
            textureArray.add(textures.findRegion(regionName));
        }
        //inicializa um array de arrays com o total de posi��es de a��es na spritesheet
        Array<Array<Animation>> animationSet = new Array<>();

        for (int i = 0; i < framesPerAction.length; i++) {
            animationSet.add(new Array<Animation>());
        }

        Array<TextureRegion> frames = new Array<>();
        //para cada regi�o separada 
        for (TextureRegion tr : textureArray) {
            //para cada a��o nas diferentes regi�es 
            for (int i = 0; i < framesPerAction.length; i++) {
                //quebra a regi�o em frames
                for (int j = 0; j < framesPerAction[i]; j++) {
                    frames.add(new TextureRegion(tr, j * frameW, i * frameH, frameW, frameH));
                }
                //o array mais externo de anima��es recebe esta anima��o
                //ele manter� em cada indice de si um array que diz respeito a mesma a��o porem em diferentes posi��es
                animationSet.get(i).add(new Animation<>(0.2f, frames, modes[i]));
                frames.clear();
            }
        }
        return animationSet;
    }

    public void update(float f) {
        super.setPosition(body.getPosition().x - super.getWidth() / 2, body.getPosition().y - super.getHeight() / 2);
        super.setRegion(getFrame(f));
    }

}
