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

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

    private Skin buttonSkin;

    private Table mainTable;
    private Label title;

    private final int menuWidth = 700;
    private final int menuHeight = 560;

    public MenuScreen(MyGdxGame game) {
        this.game = game;

    }

    private TextButton addTextButton(String name) {

        this.dark = new BitmapFont(Gdx.files.internal("Font/black_font.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/white_font.fnt"), false);

        TextButtonStyle style = new TextButtonStyle();

        style.up = buttonSkin.getDrawable("botao3");
        style.down = buttonSkin.getDrawable("botao3");
        style.over = buttonSkin.getDrawable("botao2");
        style.pressedOffsetX = 2;
        style.pressedOffsetY = 2;
        style.font = dark;

        TextButton button = new TextButton(name, style);

        button.pad(20);
        //button.padBottom(5);

        Container container = new Container(button);
        container.pad(5);

        mainTable.add(container);

        return button;
    }

    @Override
    public void show() {

        viewport = new FitViewport(800, 560);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        
        atlas = new TextureAtlas("buttons.pack");

        buttonSkin = new Skin(atlas);

        mainTable = new Table();
        mainTable.setFillParent(true);
        
        
        //mainTable.setBounds(0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        mainTable.debug();
        mainTable.center();
        mainTable.left();
        mainTable.padLeft(Gdx.graphics.getWidth() / 3);
        
        addTextButton("Alone in the West");
        mainTable.row();

        addTextButton("Play").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayableScreen((MyGdxGame) game));
            }
        });
        mainTable.row();
        addTextButton("Score");
        mainTable.row();
        addTextButton("Quit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               ;
            }
        });;
        mainTable.row();

        stage.addActor(mainTable);

    }

    @Override
    public void render(float f) {

        Gdx.gl.glClearColor(f, f, f, f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(f);
        stage.draw();
    }

    @Override
    public void resize(int width, int heigth) {

        mainTable.setX(0);
        mainTable.setY(0);
        mainTable.setWidth(menuWidth);
        mainTable.setHeight(menuHeight);

        viewport.update(menuWidth, menuHeight);
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
        buttonSkin.dispose();
    }

}
