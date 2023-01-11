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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.mobs.*;

/**
 * @author Hugo
 */
public class PlayableScreen implements Screen {

    private final int W_WIDTH = 400;
    private final int W_HEIGHT = 200;

    private final MyGdxGame game;

    //variaveis de camera
    private final OrthographicCamera cam;
    private final FitViewport port;

    //variavel de mapas
    private MapHandler currentMap;

    //variavel de entidades
    private EntityHandler entityHandler;

    private boolean isPause;
    private Hud hud;
    private boolean isroundEnded;


    public PlayableScreen(MyGdxGame game , String mapName) {
        this.game = game;
        cam = new OrthographicCamera();
        //viewport e usado pra manter a proporcao da tela
        port = new FitViewport(W_WIDTH, W_HEIGHT, cam);
        isPause = false;
        isroundEnded = false;


        MapHandler map1 = new MapHandler(port, cam, "Maps/mapa1.tmx");


        currentMap = map1;

        entityHandler = new EntityHandler(currentMap);
        entityHandler.setPlayer(new Player(this.currentMap, this.entityHandler, 80, 80));

        currentMap.getWorld().setContactListener(new CollisionListener(map1, entityHandler));
        hud = new Hud(entityHandler.getPlayer(), game.batch);
        currentMap.loadCollisionBoxes(entityHandler);
        currentMap.loadSpawners(entityHandler);

        currentMap.loadSpawners(entityHandler);

        new Coffin(map1, entityHandler, 40, 40);
        new Cactus(map1, entityHandler, 60 , 60);

    }

    //lembrar de criar ou utilizar uma classe propria pra lidar com os inputs
    //esta esta sendo utilizada para testes
    //esse metodo reune atualizacao de objetos
    //porem o ideal e ser quebrado em categorias menores relativas

    private void vefifyEnd(){
        if(this.entityHandler.vefityMobsEnded() && this.currentMap.verifySpawnersEnded()){
            this.isroundEnded = true;
            this.isPause = true;
        }
    }

    public void update(float dt) {
vefifyEnd();
        if (!isPause && Gdx.input.isKeyPressed(Input.Keys.P)) {
            pause();
        } else if (isPause && Gdx.input.isKeyPressed(Input.Keys.P)) {
            resume();
        }


        if (isPause) {
            dt = 0;
        } else {
            cam.update();

            currentMap.update(dt);
            entityHandler.update(dt);
            //camera segue o personagem
            cam.position.x = entityHandler.getPlayer().getBody().getPosition().x;
            cam.position.y = entityHandler.getPlayer().getBody().getPosition().y;
        }
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

        entityHandler.draw(game.batch);

        game.batch.end();
        hud.update();
    }

    @Override
    public void resize(int w, int h) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        port.update(w, h);
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        System.out.println("Pause");
        this.isPause = true;
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        System.out.println("Unpause");
        this.isPause = false;
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        pause();
    }

    @Override
    public void dispose() {

    }

}
