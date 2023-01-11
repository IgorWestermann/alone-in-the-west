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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.constants.GlobalConfig;
import com.mygdx.game.constants.State;
import com.mygdx.game.sprites.mobs.Cactus;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.mobs.Coffin;
import com.mygdx.game.sprites.mobs.Player;
import com.mygdx.game.sprites.objects.Spawner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class PlayableScreen implements Screen {

    private final int W_WIDTH = 400;
    private final int W_HEIGHT = 200;

    private BitmapFont light;
    private BitmapFont dark;

    private Stage stage;
    private Table mainTable;

    private Skin UIAtlas;

    private final MyGdxGame game;

    //variaveis de camera
    private final OrthographicCamera cam;
    private final FitViewport port;

    //variavel de mapas
    private MapHandler currentMap;

    //variavel de entidades
    private EntityHandler entityHandler;

    private boolean isPause;
    private boolean isroundEnded;
    private final TextureAtlas atlas;
    private Window pause;

    public PlayableScreen(MyGdxGame game, String mapName) {
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
        currentMap.loadCollisionBoxes(entityHandler);
        currentMap.loadSpawners(entityHandler);

        this.dark = new BitmapFont(Gdx.files.internal("Font/black_font.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/white_font.fnt"), false);

        this.stage = new Stage(port, game.batch);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");
        UIAtlas = new Skin(atlas);

    }

    private void vefifyEnd() {
        if (this.entityHandler.vefityMobsEnded() && this.currentMap.verifySpawnersEnded()) {
            this.isroundEnded = true;
            System.out.println("Next Round");
            //pause();
            nextRound();
        }
    }

    private void createPausePopUp() {

        Window.WindowStyle ws = new Window.WindowStyle();
        ws.titleFont = dark;
        ws.background = UIAtlas.getDrawable("placa_4");

        pause = new Window("Pause", ws);
        this.stage.addActor(pause);

        pause.setSize(20, 20);
        pause.center();

        TextButton resume = addTextButton("Voltar ao jogo", pause);
        resume.setHeight(10);
        resume.getLabel().setScale(1 / 2);
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Unpause");
                isPause = false;
            }
        });

        TextButton menu = addTextButton("Voltar ao Menu", pause);
        menu.setHeight(10);
        menu.getLabel().setScale(1 / 2);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton exit = addTextButton("Sair", pause);
        exit.setHeight(10);
        exit.getLabel().setScale(1 / 2);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        pause.add(table);

        table.setClip(true);
        table.setFillParent(true);
        table.center();
        table.add(resume).spaceBottom(1).maxHeight(20);
        table.row();
        table.add(menu).spaceBottom(1).maxHeight(20);
        table.row();
        table.add(exit).spaceBottom(1).maxHeight(20);
        table.row();
        table.pad(10);

        table.pack();

        pause.pack();

    }

    private TextButton addTextButton(String name, Window pause) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = UIAtlas.getDrawable("placa_2");
        style.down = UIAtlas.getDrawable("placa_2");
        style.over = UIAtlas.getDrawable("placa_2");
        style.pressedOffsetX = 2;
        style.pressedOffsetY = 2;
        style.font = dark;

        System.out.println(Gdx.graphics.getWidth());

        System.out.println(Gdx.graphics.getHeight());

        TextButton button = new TextButton(name, style);

        button.pad(20);
        //button.padBottom(5);

        Container container = new Container(button);
        container.pad(1);

        pause.add(container);

        return button;
    }

    public void update(float dt) {

        vefifyEnd();

        if (!isPause && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause();
        } else if (isPause && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            System.out.println("Unpause");
            this.isPause = false;
        }

        if (isPause) {
            dt = 0;
            stage.act(dt);
            //pause.setWidth(W_WIDTH / 2);
            //pause.setHeight(W_HEIGHT / 2);
            //pause.setResizable(false);
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

        if (isPause) {

            stage.draw();
        }
    }

    @Override
    public void resize(int w, int h) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        port.update(1280, 720);
    }

    @Override
    public void pause() {

        createPausePopUp();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        System.out.println("Pause");
        this.isPause = true;
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        pause();
    }

    @Override
    public void dispose() {

        light.dispose();
        dark.dispose();

        stage.dispose();

        UIAtlas.dispose();

        //variaveis de camera
        //variavel de mapas
        currentMap.dispose();
        //variavel de entidades
        atlas.dispose();

    }

    private void nextRound() {
        randomEnemyBonus();
        randomPlayerBonus();
        
        currentMap.restartSpawners();
        this.isPause = false;
        isroundEnded = false;
    }

    private int randomPlayerBonus() {
        int chance = (int) Math.round(Math.random() * 3);
        
        Player p = entityHandler.getPlayer();

        switch (chance) {
            case 0:

                System.out.println("Player ganha mais dano");
                GlobalConfig.PlayerDamageModifier++;

                break;
            case 1:

                System.out.println("Player ganharam mais velocidade");
                GlobalConfig.PlayerSpeedModifier++;

                break;

            case 2:

                System.out.println("Player ganha um ponto de vida");
                p.setHealth(p.getHealth() + 1);
                break;

            case 3:
                System.out.println("Player atira mais rapido");
                p = entityHandler.getPlayer();
                
                float frameDuration = p.getAnimations().getAnimationFrameDuration(State.SHOTING);
                
                p.getAnimations().changeAnimationSpeed(State.SHOTING, frameDuration * 0.9f);
                
                break;
            default:
                randomPlayerBonus();
        }

        return chance;
    }

    private int randomEnemyBonus() {

        int chance = (int) Math.round(Math.random() * 4);

        switch (chance) {
            case 0:

                System.out.println("Os inimigos ganharam mais dano");
                GlobalConfig.CactusDamageModifier++;
                GlobalConfig.CoffinDamageModifier++;

                break;
            case 1:

                System.out.println("Os inimigos ganharam mais velocidade");
                GlobalConfig.CactusSpeedModifier += 5;
                GlobalConfig.CoffinDamageModifier += 10;

                break;

            case 2:

                System.out.println("Os inimigos ganharam mais vida");
                GlobalConfig.CactusHealth++;
                GlobalConfig.CoffinHealth++;

                break;

            case 3:
                System.out.println("Spawners geram mais um inimigo cada");
                GlobalConfig.SpawnerMaxSpawns++;
                break;

            case 4:
                System.out.println("Spawners geram inimigos mais rapido");

                if (GlobalConfig.SpawnerCooldowns > 0.5f) {
                    GlobalConfig.SpawnerCooldowns -= 0.2;
                }

                break;

            default:
                throw new AssertionError();
        }

        return chance;

    }

}
