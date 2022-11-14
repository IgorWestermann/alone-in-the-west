/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Cactus;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.Player;

/**
 *
 * @author Hugo
 */
public class PlayableScreen implements Screen {

    private final int W_WIDTH = 400;
    private final int W_HEIGHT = 200;

    private final MyGdxGame game;

    //variavel teste de personagel
    public Entity mainCharacter;

    //variaveis de camera
    private final OrthographicCamera cam;
    private final FitViewport port;

    //variavel de mapas
    private MapHandler currentMap;

    public PlayableScreen(MyGdxGame game) {
        this.game = game;
        cam = new OrthographicCamera();
        //viewport é usado pra manter a proporção da tela
        port = new FitViewport(W_WIDTH, W_HEIGHT, cam);

        MapHandler map1 = new MapHandler(port, cam, "mapa1.tmx");

        currentMap = map1;

        mainCharacter = new Cactus(map1.getWorld(), this);
    }

    //lembrar de criar ou utilizar uma classe propria pra lidar com os inputs
    //esta esta sendo utilizada para testes
    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mainCharacter.body.setLinearVelocity(0, 0);

        } else {

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                mainCharacter.body.applyLinearImpulse(new Vector2(0, 7f), mainCharacter.body.getWorldCenter(), true);
            } else {
                mainCharacter.body.getLinearVelocity().x = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                mainCharacter.body.applyLinearImpulse(new Vector2(0, -7f), mainCharacter.body.getWorldCenter(), true);
            } else {
                mainCharacter.body.getLinearVelocity().x = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                mainCharacter.body.applyLinearImpulse(new Vector2(-7f, 0), mainCharacter.body.getWorldCenter(), true);
            } else {
                mainCharacter.body.getLinearVelocity().y = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                mainCharacter.body.applyLinearImpulse(new Vector2(7f, 0), mainCharacter.body.getWorldCenter(), true);
            } else {
                mainCharacter.body.getLinearVelocity().y = 0;
            }

        }
    }

    //esse metodo reune atualizaçao de objetos
    //porem o ideal é ser quebrado em categorias menores relativas
    public void update(float dt) {

        handleInput();
        //os dois ultimos parametros são responsaveis pelo refino dos calculos de colisão
        cam.update();
        mainCharacter.update(dt);
        //camera segue o personagem
        cam.position.x = mainCharacter.body.getPosition().x;
        cam.position.y = mainCharacter.body.getPosition().y;
        currentMap.update(dt);

    }

    @Override
    public void show() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float f) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(f);

        currentMap.render(f);
        game.batch.setProjectionMatrix(cam.combined);
        //tudo que for ser desenhado precisa estar entre batch.begin() e batch.end()
        game.batch.begin();
        mainCharacter.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int w, int h) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        port.update(w, h);
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
