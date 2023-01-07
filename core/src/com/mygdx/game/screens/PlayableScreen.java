/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.mobs.Cactus;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.mobs.Coffin;
import com.mygdx.game.sprites.mobs.Player;
import com.mygdx.game.sprites.objects.Spawner;

/**
 *
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
    
    private Spawner TESTESpawner;
    

    public PlayableScreen(MyGdxGame game) {
        this.game = game;
        cam = new OrthographicCamera();
        //viewport e usado pra manter a proporcao da tela
        port = new FitViewport(W_WIDTH, W_HEIGHT, cam);

        MapHandler map1 = new MapHandler(port, cam, "mapa1.tmx");
        currentMap = map1;
        
        entityHandler = new EntityHandler(currentMap);
        entityHandler.setPlayer(new Player(this.currentMap , this.entityHandler , 80 , 80));
        
        currentMap.getWorld().setContactListener(new CollisionListener(map1 , entityHandler));
        
        TESTESpawner = new Spawner(currentMap, entityHandler, 40, 40, 40, 2, Spawner.enemyType.RAMDON);
        
    }

    //lembrar de criar ou utilizar uma classe propria pra lidar com os inputs
    //esta esta sendo utilizada para testes
    //esse metodo reune atualizacao de objetos
    //porem o ideal e ser quebrado em categorias menores relativas
    public void update(float dt) {

        TESTESpawner.update(dt);
        
        cam.update();
        
        currentMap.update(dt);
        entityHandler.update(dt);
        //camera segue o personagem
        cam.position.x = entityHandler.getPlayer().getBody().getPosition().x;
        cam.position.y = entityHandler.getPlayer().getBody().getPosition().y;
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
