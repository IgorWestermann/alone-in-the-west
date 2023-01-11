/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Hugo
 */
public class MenuScreen implements Screen {

    private Stage stage;
    private Viewport viewport;
    private Game game;
    private TextureAtlas atlas;

    private BitmapFont light;
    private BitmapFont dark;

    private Skin UIAtlas;

    private Table mainTable;
    private TextButton title;

    private TextButtonStyle textButtonStyle;

    public MenuScreen(MyGdxGame game) {
        this.game = game;

    }

    private TextButton addTextButton(String name) {

        TextButtonStyle style = new TextButtonStyle();

        style.up = UIAtlas.getDrawable("placa_2");
        style.down = UIAtlas.getDrawable("placa_2");
        style.over = UIAtlas.getDrawable("placa_2");
        style.pressedOffsetX = 2;
        style.pressedOffsetY = 2;
        style.font = dark;

        TextButton button = new TextButton(name, style);
        Container container = new Container(button);
        mainTable.add(container);
        
        button.pad(20);
        //button.padBottom(5);
        container.pad(5);

        

        return button;
    }

    @Override
    public void show() {

        this.dark = new BitmapFont(Gdx.files.internal("Font/dark.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/dark.fnt"), false);

        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");
        UIAtlas = new Skin(atlas);

        mainTable = new Table();
        stage.addActor(mainTable);

        mainTable.setClip(true);
        mainTable.setFillParent(true);
        //mainTable.debug();
        mainTable.center();
        mainTable.align(1);
        mainTable.setBackground(new TextureRegionDrawable(new Texture("UI/background.png")));

        //mainTable.padLeft(Gdx.graphics.getWidth() / 3);
        this.textButtonStyle = new TextButtonStyle();
        this.textButtonStyle.up = UIAtlas.getDrawable("placa_3");
        this.textButtonStyle.font = light;
        this.textButtonStyle.fontColor = Color.WHITE;

        this.title = new TextButton("Alone in the West", textButtonStyle);
        mainTable.add(title);
        title.pad(20);
        title.getLabel().setScale(3);

        title.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                dispose();
                game.setScreen(new HiddenScreen((MyGdxGame) game ));
            }
        });
        mainTable.row();

        addTextButton("Play").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PlayableScreen((MyGdxGame) game, "Maps/mapa1.tmx"));
            }
        });
        mainTable.row();
        addTextButton("Score").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                dispose();
                game.setScreen((Screen) new ScoreScreen((MyGdxGame) game));
            }
        });
        mainTable.row();
        addTextButton("Quit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });;
        mainTable.row();

    }

    @Override
    public void render(float f) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        mainTable.setClip(true);
        mainTable.setFillParent(true);
        mainTable.setTransform(true);
        mainTable.setBounds(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

        stage.act(f);
        stage.draw();
    }

    @Override
    public void resize(int width, int heigth) {

        viewport.update(1280, 720);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        light.dispose();
        dark.dispose();
        UIAtlas.dispose();
    }

}
