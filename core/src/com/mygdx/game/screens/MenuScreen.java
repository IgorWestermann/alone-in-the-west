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

    private final int menuWidth = 700;
    private final int menuHeight = 560;
    private TextButtonStyle textButtonStyle;

    public MenuScreen(MyGdxGame game) {
        this.game = game;

    }

    private TextButton addTextButton(String name) {

        TextButtonStyle style = new TextButtonStyle();

        style.up = UIAtlas.getDrawable("placa_1");
        style.down = UIAtlas.getDrawable("placa_1");
        style.over = UIAtlas.getDrawable("placa_1");
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

        this.dark = new BitmapFont(Gdx.files.internal("Font/black_font.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/white_font.fnt"), false);

        viewport = new FitViewport(800, 560);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");

        LabelStyle labelStyle = new LabelStyle(light, Color.WHITE);

        UIAtlas = new Skin(atlas);

        mainTable = new Table();
        mainTable.setClip(true);
        mainTable.setFillParent(true);
        mainTable.debug();
        mainTable.center();
        mainTable.align(1);
        mainTable.setBackground(new TextureRegionDrawable(new Texture("UI/background.png")));

        //mainTable.padLeft(Gdx.graphics.getWidth() / 3);
        this.textButtonStyle = new TextButtonStyle();
        this.textButtonStyle.up = UIAtlas.getDrawable("placa_3");
        this.textButtonStyle.font = light;
        this.textButtonStyle.fontColor = Color.WHITE;

        this.title = new TextButton("Alone in the West", textButtonStyle);
        title.pad(20);
        title.getLabel().setScale(3);

        title.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new Settings((MyGdxGame) game));
            }
        });
        mainTable.add(title);

        mainTable.row();

        Screen thisScreen = this;

        addTextButton("Play").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayableScreen((MyGdxGame) game, "Maps/mapa1.tmx"));
            }
        });
        mainTable.row();
        addTextButton("Score").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen((Screen) new HiddenScreen((MyGdxGame) game));
            }
        });
        mainTable.row();
        addTextButton("Quit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });;
        mainTable.row();

        stage.addActor(mainTable);

    }

    @Override
    public void render(float f) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.update(menuWidth, menuHeight);

        mainTable.setClip(true);
        mainTable.setFillParent(true);
        mainTable.setTransform(true);
        mainTable.setBounds(viewport.getScreenX() , viewport.getScreenY(),  viewport.getScreenWidth() , viewport.getScreenHeight());

        stage.act(f);
        stage.draw();
    }

    @Override
    public void resize(int width, int heigth) {

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
        UIAtlas.dispose();
    }

}
