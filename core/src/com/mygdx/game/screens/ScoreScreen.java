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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

/**
 *
 * @author Hugo
 */
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

        viewport = new FitViewport(800, 560);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");

        UIAtlas = new Skin(atlas);

        this.ls = new Label.LabelStyle(light, Color.WHITE);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.debug();
        mainTable.center();

        scroll = new ScrollPane(mainTable);
        outerContainer = new Table();
        outerContainer.background(UIAtlas.getDrawable("placa_4"));
        outerContainer.add(scroll).width(800).height(560);
        outerContainer.align(1).center().setX(0);

        readCSVData();
    }

    public Array<Pair<String, Integer>> readCSVData() {

        FileHandle handle;
        File source;
        Scanner scanner;

        try {
            handle = Gdx.files.internal("score_register.csv");
            source = handle.file();
            scanner = new Scanner(source);
            
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.println(line);
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ScoreScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Array<>();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
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
