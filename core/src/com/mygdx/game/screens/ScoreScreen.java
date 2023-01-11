/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.constants.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ScoreScreen implements Screen {

    private BitmapFont dark;
    private BitmapFont light;
    private FitViewport viewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin UIAtlas;
    private Table mainTable;
    private final MyGdxGame game;
    private Label.LabelStyle ls;
    private Label title;
    private TextField.TextFieldStyle tfs;
    private ScrollPane scroll;
    private Table outerContainer;

    public ScoreScreen(MyGdxGame game) {

        this.game = game;
    }

    @Override
    public void show() {

        this.dark = new BitmapFont(Gdx.files.internal("Font/black_font.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/white_font.fnt"), false);

        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("UI/AtlasUI.pack");
        UIAtlas = new Skin(atlas);
        this.ls = new Label.LabelStyle(light, Color.WHITE);

        TextureRegionDrawable back = (TextureRegionDrawable) UIAtlas.getDrawable("placa_2");
        TextureRegionDrawable cursor = (TextureRegionDrawable) UIAtlas.getDrawable("skull_knob");

        tfs = new TextField.TextFieldStyle(dark, Color.BLACK, cursor, cursor, back);

        mainTable = new Table();
        scroll = new ScrollPane(mainTable);

        mainTable.setFillParent(true);
        mainTable.pad(20);
        mainTable.padBottom(400);
        outerContainer = new Table();
        stage.addActor(outerContainer);
        outerContainer.setClip(true);
        outerContainer.setFillParent(true);

        outerContainer.background(UIAtlas.getDrawable("placa_4"));
        outerContainer.add(scroll);
        outerContainer.align(1).center();

        Array<Pair<String, Integer>> array = readCSVData();

        for (Pair p : array) {
            addEntry(p);
        }

        array.clear();
        
        addTextButton("Voltar ao menu").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MenuScreen((MyGdxGame) game));
            }
        });
    }

    private TextButton addTextButton(String name) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

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
        container.pad(5);

        return button;
    }

    private void addEntry(Pair<String, Integer> pair) {

        Label.LabelStyle ls = new Label.LabelStyle(light, Color.WHITE);
        Label label = new Label(pair.first, ls);

        Container labelContainer = new Container(label);
        mainTable.add(labelContainer);

        labelContainer.pad(20);

        Label value = new Label(String.valueOf(pair.second), ls);

        Container valueContainer = new Container(value);
        mainTable.add(valueContainer);

        valueContainer.pad(20);

        mainTable.row().spaceBottom(2);

    }

    public Array<Pair<String, Integer>> readCSVData() {

        FileHandle handle;
        File source;
        Scanner scanner = null;

        Array<Pair<String, Integer>> array = new Array<>();

        try {
            handle = Gdx.files.internal("score_register.csv");
            source = handle.file();
            scanner = new Scanner(source);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.replace(" ", "");
                String[] dados = line.split(",");

                try {
                    String name = dados[0];
                    int score = Integer.parseInt(dados[1]);

                    array.add(new Pair<>(name, score));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ScoreScreen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return array;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
    }

}
